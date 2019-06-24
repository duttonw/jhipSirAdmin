package au.gov.qld.sir.entity;

enum TagType {
	CATEGORY("Category"),
	APPLICANT_TYPE("Applicant Type"),
	CLIENT_GROUP("Client Group"),
	BUSINESS_SPECIFIC_ACTIVITY("Business Specific Activity"),
	ASSISTANCE_TYPE("Assistance Type");

	private final String text;

	TagType(final String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
