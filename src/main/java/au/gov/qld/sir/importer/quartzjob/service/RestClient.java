package au.gov.qld.sir.importer.quartzjob.service;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Wrapper for a HTTP request
 * @author Kellie
 *
 */
public class RestClient {

    private RequestType requestType;
    private String url;
    private String body;
    private Map<String, String> headers = new HashMap<String, String>();
    private int statusCode;
    private CloseableHttpClient client;
    private String result;

    /**
     * Init Generic Rest Client
     * @param requestType
     */
    public RestClient(RequestType requestType) {
        this.client = HttpClients.createMinimal();
        this.requestType = requestType;
    }

    public String execute(String url) throws Exception {
        this.url = url;
        return this.execute();
    }

    public String execute(String url, String body) throws Exception {
        this.url = url;
        this.body = body;
        return this.execute();
    }

    /**
     * A HTTP status code of 999 is a result of a fail to make the request
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
        AbstractHttpMessage request = null;
        HttpResponse response = null;
        this.statusCode = 999;

        // Set the uri and body
        if (this.requestType == null) {
            throw new Exception("Null request type.");
        }
        if (this.url == null) {
            throw new Exception("Null URL.");
        }

        if (this.requestType == RequestType.POST) {
            request = new HttpPost(this.url);
            if (this.body.length() != 0) {
                StringEntity entity = new StringEntity(this.body);
                entity.setContentType("application/json");
                ((HttpPost)request).setEntity(entity);
            }
            addToRequestHeaders(request);
            response = this.client.execute((HttpPost) request);
            StringWriter writer = new StringWriter();
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity.getContent() != null) {
                IOUtils.copy(responseEntity.getContent(), writer);
                this.result = writer.toString();
            } else {
                this.result = null;
            }
            this.statusCode = response.getStatusLine().getStatusCode();

        } else if (this.requestType == RequestType.GET) {
            request = new HttpGet(this.url);
            addToRequestHeaders(request);
            response = this.client.execute((HttpGet) request);
            StringWriter writer = new StringWriter();
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity.getContent() != null) {
                IOUtils.copy(responseEntity.getContent(), writer);
                this.result = writer.toString();
            } else {
                this.result = null;
            }
            this.statusCode = response.getStatusLine().getStatusCode();
        }

        if (this.statusCode > 299 || this.statusCode < 200) {
            throw new RuntimeException("Failed : HTTP error code : " + this.statusCode + " - " + this.result);
        }

        return this.result;
    }

    /**
     * Add headers to request
     * @param request
     */
    private void addToRequestHeaders(AbstractHttpMessage request) {
        // Add headers
        Iterator<?> it = this.headers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
            request.addHeader(pair.getKey(), pair.getValue());
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public void setClient(CloseableHttpClient client) {
        this.client = client;
    }
}
