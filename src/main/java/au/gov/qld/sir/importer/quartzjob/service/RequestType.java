package au.gov.qld.sir.importer.quartzjob.service;

import org.codehaus.jackson.annotate.JsonValue;

public enum RequestType {
	GET("Get"), POST("Post");

	private String name;

	private RequestType(final String name) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
