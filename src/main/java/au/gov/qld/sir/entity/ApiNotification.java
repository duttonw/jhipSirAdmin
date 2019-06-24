package au.gov.qld.sir.entity;

@Deprecated
public class ApiNotification {
	private String recordId;
   
	private String QId;
	
	private String certIteractionId;
	
	private String source;
	
	private String reference;
	
	private String senderEmail;
	
	private String scheduledDate;
	
	private String scheduledTime;
	
	private String email;
	
	private String mobileNumber;
	
	private String messageSubject;
	
	private String messageText;
	
	private String messageHtml;
	
	private String messageSms;
	
	private boolean sendEmail;
	
	private boolean sendSMS;
	
	private String templateIdentifier;
	
	private String templateText;
	
	public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    
    public String getQId() {
        return QId;
    }
    public void setQId(String QId) {
        this.QId = QId;
    }
    
	public String getCertIteractionId() {
        return certIteractionId;
    }
    public void setCertIteractionId(String certIteractionId) {
        this.certIteractionId = certIteractionId;
    }
    
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    
    public String getSenderEmail() {
        return senderEmail;
    }
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
    
    public String getScheduledDate() {
        return scheduledDate;
    }
    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
    
    public String getScheduledTime() {
        return scheduledTime;
    }
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public String getMessageSubject() {
        return messageSubject;
    }
    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }
    
    public String getMessageText() {
        return messageText;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    public String getMessageHtml() {
        return messageHtml;
    }
    public void setMessageHtml(String messageHtml) {
        this.messageHtml = messageHtml;
    }
    
    public String getMessageSms() {
        return messageSms;
    }
    public void setMessageSms(String messageSms) {
        this.messageSms = messageSms;
    }
    
    public boolean getSendEmail() {
        return sendEmail;
    }
    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }
    
    public boolean getSendSMS() {
        return sendSMS;
    }
    public void setSendSMS(boolean sendSMS) {
        this.sendSMS = sendSMS;
    }
    
    public String getTemplateIdentifier() {
        return templateIdentifier;
    }
    public void setTemplateIdentifier(String templateIdentifier) {
        this.templateIdentifier = templateIdentifier;
    }
    
    public String getTemplateText() {
        return templateText;
    }
    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }

}
