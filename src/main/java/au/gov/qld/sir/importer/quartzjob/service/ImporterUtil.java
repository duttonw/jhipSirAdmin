package au.gov.qld.sir.importer.quartzjob.service;

import au.gov.qld.sir.entity.ApiNotification;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.csv.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImporterUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImporterUtil.class);

	// Number of results to bring back from Open Data using the package search (defaults to 10)
	private static final int OPENDATA_RESULT_ROWS = 1000;

	/**
	 * Check if there has been an update in the time specificed by the import Trigger
	 *
	 * @param date          The date last modified of the resource, @NotNull
	 * @param importTrigger The number of hours to use when checking for an update
	 * @return True if date is within the time period between importTrigger hours ago and now.
	 */
	public static Boolean hasUpdated(String date, int importTrigger) {
		Objects.requireNonNull(date, "Date must be set");

		// Matches date of form  2018-04-04T00:00:00
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			// Difference between NOW and the date provided
			long timeDiff = new Date().getTime() - formatter.parse(date).getTime();
			
			boolean beforeNow = timeDiff > 0;
			
			boolean afterLastTrigger = timeDiff < (importTrigger * 60 * 60 * 1000);
			
			return beforeNow && afterLastTrigger;
			
		} catch (java.text.ParseException e) {
			LOGGER.error("Failed to get the last updated date", e);
		}
		return false;
	}

	// Convert CSV string to JSON String

	/**
	 * Convert a CSV to a JSON string
	 *
	 * @param csvData
	 * @param maintainer
	 * @return json string
	 * @throws IOException
	 * @throws JSONException
	 */
	public static String CSVtoJSON(String csvData, String maintainer) throws IOException, JSONException {

		CsvReader reader = CsvParser.reader(csvData);
		OutputStream out = new ByteArrayOutputStream();
		JsonFactory jsonFactory = new JsonFactory();

		Iterator<String[]> iterator = reader.iterator();
		String[] headers = iterator.next();

		try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(out)) {

			jsonGenerator.writeStartArray();

			while (iterator.hasNext()) {
				jsonGenerator.writeStartObject();
				String[] values = iterator.next();
				int nbCells = Math.min(values.length, headers.length);
				for (int i = 0; i < nbCells; i++) {
					if (values[i].equals("")) {
						values[i] = null;
					}
					jsonGenerator.writeFieldName(headers[i]);
					jsonGenerator.writeString(values[i]);
				}
				jsonGenerator.writeFieldName("maintainer");
				jsonGenerator.writeString(maintainer);
				jsonGenerator.writeEndObject();
			}
			jsonGenerator.writeEndArray();

		}
		return out.toString();
	}

	/**
	 * Given a resource id fetch the last modified date
	 *
	 * @param revisionId  The revision id of the resource revision to get the last modified date for
	 * @param importerApi The api URL for Open Data
	 * @return The last modified date string. Returns null on failure
	 */
	public static String getOpenDataResourceLastModified(String revisionId, String importerApi) {
		Pattern matchApiBase = Pattern.compile("(.*api\\/)");
		Matcher matcher = matchApiBase.matcher(importerApi);

		// Break down our api url and build the new request to avoid the need for more configuration
		if (matcher.find()) {
			String baseApi = matcher.group(1);
			String detailsApi = String.format("%s3/action/revision_show?id=%s", baseApi, revisionId);

			RestClient rc = new RestClient(RequestType.GET);
			rc.setUrl(detailsApi);

			try {
				String result = rc.execute();
				JSONObject importerResult = new JSONObject(result);
				importerResult = (JSONObject) importerResult.get("result");
				return importerResult.get("timestamp").toString();
			} catch (Exception e) {
				LOGGER.error("Failed to get the last modified date for the open data resource", e);
				return null;
			}
		} else {
			LOGGER.error("Cannot find base api path");
			return null;
		}
	}

	/**
	 * Fetch data from the OpenData API and pass it on for processing
	 * <p>
	 * NOTE: This only ever returns the data from the URL of the FIRST Resource.
	 * IT should only be used for Grants where there is one single merged file.
	 *
	 * @param importerApi     API to hit
	 * @param importerKeyword The keyword that matches the dataset that will be searched for
	 * @param importTrigger
	 */
	public JSONArray importerApi(String importerApi, String importerKeyword, int importTrigger) {

		LOGGER.debug(String.format("OpenDataImporterRecord API --- %s", importerApi));
		String[] keywords = importerKeyword.split(",");

		for (String keyword : keywords) {
			//by default opendata will only show the top 10 matches.  If we're retriving results this way we need to set rows
			String api = String.format("%s%s&rows=%d", importerApi, keyword, OPENDATA_RESULT_ROWS);
			RestClient rc = new RestClient(RequestType.GET);
			rc.setUrl(api);

			try {
				String result = rc.execute();
				JSONObject importerResult = new JSONObject(result);
				importerResult = (JSONObject) importerResult.get("result");
				JSONArray resultSet = new JSONArray(importerResult.get("results").toString());

				Integer totalResultCount = Integer.parseInt(importerResult.get("count").toString());
				LOGGER.info("Processing {} out of {} Results set from '{}' OpenDataImporterRecord", resultSet.length(), totalResultCount, keyword);

				for (int i = 0; i < resultSet.length(); i++) {
					importerResult = resultSet.getJSONObject(i);
					String maintainerEmail = importerResult.get("maintainer").toString();
					JSONArray resources = new JSONArray(importerResult.get("resources").toString());

					JSONObject resource;

					for (int j = 0; j < resources.length(); j++) {

						resource = resources.getJSONObject(j);
						String resourceName = resource.get("name").toString();

						String lastModified = getOpenDataResourceLastModified(resource.get("revision_id").toString(), importerApi);
						Objects.requireNonNull(lastModified, "Last Modified result is null, it could not be fetched.");

						if (hasUpdated(lastModified, importTrigger)) {
							LOGGER.info("Importing modified resource: {}", resourceName);
							String format = resource.get("format").toString();
							if (format.equalsIgnoreCase("csv")) {
								rc.setUrl(resource.get("url").toString());
								String csvData = rc.execute();
								return new JSONArray(CSVtoJSON(csvData, maintainerEmail));
							}
						} else {
							LOGGER.info("{}: not modified", resourceName);
							return null;
						}
					}
				}
			} catch (Exception e) {
				LOGGER.error("Error processing Import for {}: ", keyword, e);
			}
		}

		return null;
	}

	// Inject controller

	/**
	 * Send the notification using the the notification API.
	 *
	 * @param notification    @NotNull
	 * @param notificationApi @NotNull
	 * @param notificationKey @NotNull
	 *
	 * @return Result of the sir api call
	 */
	public static String sendNotification(ApiNotification notification, String notificationApi, String notificationKey) {
		Objects.requireNonNull(notification, "Notification object must be set");
	    //TODO: send email when notification is required.
		return "replace sendNotification with Email Notification";
	}
}
