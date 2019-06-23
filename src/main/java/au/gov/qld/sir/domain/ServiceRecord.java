package au.gov.qld.sir.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceRecord.
 */
@Entity
@Table(name = "service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "created_by", length = 255)
    private String createdBy;

    @Column(name = "created_date_time")
    private Instant createdDateTime;

    @Size(max = 255)
    @Column(name = "modified_by", length = 255)
    private String modifiedBy;

    @Column(name = "modified_date_time")
    private Instant modifiedDateTime;

    @Column(name = "version")
    private Long version;

    @Size(max = 1)
    @Column(name = "active", length = 1)
    private String active;

    @Size(max = 255)
    @Column(name = "eligibility", length = 255)
    private String eligibility;

    @Size(max = 255)
    @Column(name = "fees", length = 255)
    private String fees;

    @Size(max = 255)
    @Column(name = "group_header", length = 255)
    private String groupHeader;

    @Size(max = 255)
    @Column(name = "group_id", length = 255)
    private String groupId;

    @Size(max = 255)
    @Column(name = "interaction_id", length = 255)
    private String interactionId;

    @Size(max = 255)
    @Column(name = "keywords", length = 255)
    private String keywords;

    @Size(max = 255)
    @Column(name = "pre_requisites", length = 255)
    private String preRequisites;

    @Size(max = 255)
    @Column(name = "qgs_service_id", length = 255)
    private String qgsServiceId;

    @Size(max = 255)
    @Column(name = "reference_url", length = 255)
    private String referenceUrl;

    @Size(max = 255)
    @Column(name = "service_name", length = 255)
    private String serviceName;

    @Column(name = "validated_date")
    private Instant validatedDate;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 255)
    @Column(name = "pre_requisites_new", length = 255)
    private String preRequisitesNew;

    @Size(max = 255)
    @Column(name = "reference_url_new", length = 255)
    private String referenceUrlNew;

    @Size(max = 255)
    @Column(name = "eligibility_new", length = 255)
    private String eligibilityNew;

    @Size(max = 255)
    @Column(name = "service_context", length = 255)
    private String serviceContext;

    @Size(max = 255)
    @Column(name = "long_description", length = 255)
    private String longDescription;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 1)
    @Column(name = "roadmap_login_required", length = 1)
    private String roadmapLoginRequired;

    @Size(max = 1)
    @Column(name = "roadmap_customer_id_required", length = 1)
    private String roadmapCustomerIdRequired;

    @Size(max = 1)
    @Column(name = "roadmap_customer_details", length = 1)
    private String roadmapCustomerDetails;

    @Size(max = 1)
    @Column(name = "roadmap_improve_intention", length = 1)
    private String roadmapImproveIntention;

    @Size(max = 1)
    @Column(name = "roadmap_improve_future", length = 1)
    private String roadmapImproveFuture;

    @Size(max = 255)
    @Column(name = "roadmap_improve_type", length = 255)
    private String roadmapImproveType;

    @Size(max = 255)
    @Column(name = "roadmap_improve_when", length = 255)
    private String roadmapImproveWhen;

    @Size(max = 255)
    @Column(name = "roadmap_improve_how", length = 255)
    private String roadmapImproveHow;

    @Size(max = 255)
    @Column(name = "roadmap_maturity_current", length = 255)
    private String roadmapMaturityCurrent;

    @Size(max = 255)
    @Column(name = "roadmap_maturity_desired", length = 255)
    private String roadmapMaturityDesired;

    @Size(max = 255)
    @Column(name = "roadmap_comments", length = 255)
    private String roadmapComments;

    @Size(max = 255)
    @Column(name = "how_to", length = 255)
    private String howTo;

    @ManyToOne
    @JsonIgnoreProperties("serviceRecords")
    private ServiceDeliveryOrganisation deliveryOrg;

    @ManyToOne
    @JsonIgnoreProperties("serviceRecords")
    private ServiceRecord parentService;

    @ManyToOne
    @JsonIgnoreProperties("serviceRecords")
    private ServiceFranchise serviceFranchise;

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ApplicationServiceOverride> applicationServiceOverrides = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IntegrationMapping> integrationMappings = new HashSet<>();

    @OneToMany(mappedBy = "parentService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceRecord> serviceRecords = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceDelivery> serviceDeliveries = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceDeliveryForm> serviceDeliveryForms = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceDescription> serviceDescriptions = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceEvent> serviceEvents = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceEvidence> serviceEvidences = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceGroup> serviceGroups = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceName> serviceNames = new HashSet<>();

    @OneToMany(mappedBy = "fromService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceRelationship> fromServices = new HashSet<>();

    @OneToMany(mappedBy = "toService")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceRelationship> toServices = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceSupportRole> serviceSupportRoles = new HashSet<>();

    @OneToMany(mappedBy = "serviceRecord")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ServiceTagItems> serviceTagItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ServiceRecord createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDateTime() {
        return createdDateTime;
    }

    public ServiceRecord createdDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
        return this;
    }

    public void setCreatedDateTime(Instant createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public ServiceRecord modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDateTime() {
        return modifiedDateTime;
    }

    public ServiceRecord modifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
        return this;
    }

    public void setModifiedDateTime(Instant modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    public Long getVersion() {
        return version;
    }

    public ServiceRecord version(Long version) {
        this.version = version;
        return this;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getActive() {
        return active;
    }

    public ServiceRecord active(String active) {
        this.active = active;
        return this;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getEligibility() {
        return eligibility;
    }

    public ServiceRecord eligibility(String eligibility) {
        this.eligibility = eligibility;
        return this;
    }

    public void setEligibility(String eligibility) {
        this.eligibility = eligibility;
    }

    public String getFees() {
        return fees;
    }

    public ServiceRecord fees(String fees) {
        this.fees = fees;
        return this;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getGroupHeader() {
        return groupHeader;
    }

    public ServiceRecord groupHeader(String groupHeader) {
        this.groupHeader = groupHeader;
        return this;
    }

    public void setGroupHeader(String groupHeader) {
        this.groupHeader = groupHeader;
    }

    public String getGroupId() {
        return groupId;
    }

    public ServiceRecord groupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInteractionId() {
        return interactionId;
    }

    public ServiceRecord interactionId(String interactionId) {
        this.interactionId = interactionId;
        return this;
    }

    public void setInteractionId(String interactionId) {
        this.interactionId = interactionId;
    }

    public String getKeywords() {
        return keywords;
    }

    public ServiceRecord keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPreRequisites() {
        return preRequisites;
    }

    public ServiceRecord preRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
        return this;
    }

    public void setPreRequisites(String preRequisites) {
        this.preRequisites = preRequisites;
    }

    public String getQgsServiceId() {
        return qgsServiceId;
    }

    public ServiceRecord qgsServiceId(String qgsServiceId) {
        this.qgsServiceId = qgsServiceId;
        return this;
    }

    public void setQgsServiceId(String qgsServiceId) {
        this.qgsServiceId = qgsServiceId;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public ServiceRecord referenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
        return this;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceRecord serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Instant getValidatedDate() {
        return validatedDate;
    }

    public ServiceRecord validatedDate(Instant validatedDate) {
        this.validatedDate = validatedDate;
        return this;
    }

    public void setValidatedDate(Instant validatedDate) {
        this.validatedDate = validatedDate;
    }

    public String getDescription() {
        return description;
    }

    public ServiceRecord description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreRequisitesNew() {
        return preRequisitesNew;
    }

    public ServiceRecord preRequisitesNew(String preRequisitesNew) {
        this.preRequisitesNew = preRequisitesNew;
        return this;
    }

    public void setPreRequisitesNew(String preRequisitesNew) {
        this.preRequisitesNew = preRequisitesNew;
    }

    public String getReferenceUrlNew() {
        return referenceUrlNew;
    }

    public ServiceRecord referenceUrlNew(String referenceUrlNew) {
        this.referenceUrlNew = referenceUrlNew;
        return this;
    }

    public void setReferenceUrlNew(String referenceUrlNew) {
        this.referenceUrlNew = referenceUrlNew;
    }

    public String getEligibilityNew() {
        return eligibilityNew;
    }

    public ServiceRecord eligibilityNew(String eligibilityNew) {
        this.eligibilityNew = eligibilityNew;
        return this;
    }

    public void setEligibilityNew(String eligibilityNew) {
        this.eligibilityNew = eligibilityNew;
    }

    public String getServiceContext() {
        return serviceContext;
    }

    public ServiceRecord serviceContext(String serviceContext) {
        this.serviceContext = serviceContext;
        return this;
    }

    public void setServiceContext(String serviceContext) {
        this.serviceContext = serviceContext;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public ServiceRecord longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getName() {
        return name;
    }

    public ServiceRecord name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ServiceRecord startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ServiceRecord endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRoadmapLoginRequired() {
        return roadmapLoginRequired;
    }

    public ServiceRecord roadmapLoginRequired(String roadmapLoginRequired) {
        this.roadmapLoginRequired = roadmapLoginRequired;
        return this;
    }

    public void setRoadmapLoginRequired(String roadmapLoginRequired) {
        this.roadmapLoginRequired = roadmapLoginRequired;
    }

    public String getRoadmapCustomerIdRequired() {
        return roadmapCustomerIdRequired;
    }

    public ServiceRecord roadmapCustomerIdRequired(String roadmapCustomerIdRequired) {
        this.roadmapCustomerIdRequired = roadmapCustomerIdRequired;
        return this;
    }

    public void setRoadmapCustomerIdRequired(String roadmapCustomerIdRequired) {
        this.roadmapCustomerIdRequired = roadmapCustomerIdRequired;
    }

    public String getRoadmapCustomerDetails() {
        return roadmapCustomerDetails;
    }

    public ServiceRecord roadmapCustomerDetails(String roadmapCustomerDetails) {
        this.roadmapCustomerDetails = roadmapCustomerDetails;
        return this;
    }

    public void setRoadmapCustomerDetails(String roadmapCustomerDetails) {
        this.roadmapCustomerDetails = roadmapCustomerDetails;
    }

    public String getRoadmapImproveIntention() {
        return roadmapImproveIntention;
    }

    public ServiceRecord roadmapImproveIntention(String roadmapImproveIntention) {
        this.roadmapImproveIntention = roadmapImproveIntention;
        return this;
    }

    public void setRoadmapImproveIntention(String roadmapImproveIntention) {
        this.roadmapImproveIntention = roadmapImproveIntention;
    }

    public String getRoadmapImproveFuture() {
        return roadmapImproveFuture;
    }

    public ServiceRecord roadmapImproveFuture(String roadmapImproveFuture) {
        this.roadmapImproveFuture = roadmapImproveFuture;
        return this;
    }

    public void setRoadmapImproveFuture(String roadmapImproveFuture) {
        this.roadmapImproveFuture = roadmapImproveFuture;
    }

    public String getRoadmapImproveType() {
        return roadmapImproveType;
    }

    public ServiceRecord roadmapImproveType(String roadmapImproveType) {
        this.roadmapImproveType = roadmapImproveType;
        return this;
    }

    public void setRoadmapImproveType(String roadmapImproveType) {
        this.roadmapImproveType = roadmapImproveType;
    }

    public String getRoadmapImproveWhen() {
        return roadmapImproveWhen;
    }

    public ServiceRecord roadmapImproveWhen(String roadmapImproveWhen) {
        this.roadmapImproveWhen = roadmapImproveWhen;
        return this;
    }

    public void setRoadmapImproveWhen(String roadmapImproveWhen) {
        this.roadmapImproveWhen = roadmapImproveWhen;
    }

    public String getRoadmapImproveHow() {
        return roadmapImproveHow;
    }

    public ServiceRecord roadmapImproveHow(String roadmapImproveHow) {
        this.roadmapImproveHow = roadmapImproveHow;
        return this;
    }

    public void setRoadmapImproveHow(String roadmapImproveHow) {
        this.roadmapImproveHow = roadmapImproveHow;
    }

    public String getRoadmapMaturityCurrent() {
        return roadmapMaturityCurrent;
    }

    public ServiceRecord roadmapMaturityCurrent(String roadmapMaturityCurrent) {
        this.roadmapMaturityCurrent = roadmapMaturityCurrent;
        return this;
    }

    public void setRoadmapMaturityCurrent(String roadmapMaturityCurrent) {
        this.roadmapMaturityCurrent = roadmapMaturityCurrent;
    }

    public String getRoadmapMaturityDesired() {
        return roadmapMaturityDesired;
    }

    public ServiceRecord roadmapMaturityDesired(String roadmapMaturityDesired) {
        this.roadmapMaturityDesired = roadmapMaturityDesired;
        return this;
    }

    public void setRoadmapMaturityDesired(String roadmapMaturityDesired) {
        this.roadmapMaturityDesired = roadmapMaturityDesired;
    }

    public String getRoadmapComments() {
        return roadmapComments;
    }

    public ServiceRecord roadmapComments(String roadmapComments) {
        this.roadmapComments = roadmapComments;
        return this;
    }

    public void setRoadmapComments(String roadmapComments) {
        this.roadmapComments = roadmapComments;
    }

    public String getHowTo() {
        return howTo;
    }

    public ServiceRecord howTo(String howTo) {
        this.howTo = howTo;
        return this;
    }

    public void setHowTo(String howTo) {
        this.howTo = howTo;
    }

    public ServiceDeliveryOrganisation getDeliveryOrg() {
        return deliveryOrg;
    }

    public ServiceRecord deliveryOrg(ServiceDeliveryOrganisation serviceDeliveryOrganisation) {
        this.deliveryOrg = serviceDeliveryOrganisation;
        return this;
    }

    public void setDeliveryOrg(ServiceDeliveryOrganisation serviceDeliveryOrganisation) {
        this.deliveryOrg = serviceDeliveryOrganisation;
    }

    public ServiceRecord getParentService() {
        return parentService;
    }

    public ServiceRecord parentService(ServiceRecord serviceRecord) {
        this.parentService = serviceRecord;
        return this;
    }

    public void setParentService(ServiceRecord serviceRecord) {
        this.parentService = serviceRecord;
    }

    public ServiceFranchise getServiceFranchise() {
        return serviceFranchise;
    }

    public ServiceRecord serviceFranchise(ServiceFranchise serviceFranchise) {
        this.serviceFranchise = serviceFranchise;
        return this;
    }

    public void setServiceFranchise(ServiceFranchise serviceFranchise) {
        this.serviceFranchise = serviceFranchise;
    }

    public Set<ApplicationServiceOverride> getApplicationServiceOverrides() {
        return applicationServiceOverrides;
    }

    public ServiceRecord applicationServiceOverrides(Set<ApplicationServiceOverride> applicationServiceOverrides) {
        this.applicationServiceOverrides = applicationServiceOverrides;
        return this;
    }

    public ServiceRecord addApplicationServiceOverride(ApplicationServiceOverride applicationServiceOverride) {
        this.applicationServiceOverrides.add(applicationServiceOverride);
        applicationServiceOverride.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeApplicationServiceOverride(ApplicationServiceOverride applicationServiceOverride) {
        this.applicationServiceOverrides.remove(applicationServiceOverride);
        applicationServiceOverride.setServiceRecord(null);
        return this;
    }

    public void setApplicationServiceOverrides(Set<ApplicationServiceOverride> applicationServiceOverrides) {
        this.applicationServiceOverrides = applicationServiceOverrides;
    }

    public Set<IntegrationMapping> getIntegrationMappings() {
        return integrationMappings;
    }

    public ServiceRecord integrationMappings(Set<IntegrationMapping> integrationMappings) {
        this.integrationMappings = integrationMappings;
        return this;
    }

    public ServiceRecord addIntegrationMapping(IntegrationMapping integrationMapping) {
        this.integrationMappings.add(integrationMapping);
        integrationMapping.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeIntegrationMapping(IntegrationMapping integrationMapping) {
        this.integrationMappings.remove(integrationMapping);
        integrationMapping.setServiceRecord(null);
        return this;
    }

    public void setIntegrationMappings(Set<IntegrationMapping> integrationMappings) {
        this.integrationMappings = integrationMappings;
    }

    public Set<ServiceRecord> getServiceRecords() {
        return serviceRecords;
    }

    public ServiceRecord serviceRecords(Set<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
        return this;
    }

    public ServiceRecord addServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecords.add(serviceRecord);
        serviceRecord.setParentService(this);
        return this;
    }

    public ServiceRecord removeServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecords.remove(serviceRecord);
        serviceRecord.setParentService(null);
        return this;
    }

    public void setServiceRecords(Set<ServiceRecord> serviceRecords) {
        this.serviceRecords = serviceRecords;
    }

    public Set<ServiceDelivery> getServiceDeliveries() {
        return serviceDeliveries;
    }

    public ServiceRecord serviceDeliveries(Set<ServiceDelivery> serviceDeliveries) {
        this.serviceDeliveries = serviceDeliveries;
        return this;
    }

    public ServiceRecord addServiceDelivery(ServiceDelivery serviceDelivery) {
        this.serviceDeliveries.add(serviceDelivery);
        serviceDelivery.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceDelivery(ServiceDelivery serviceDelivery) {
        this.serviceDeliveries.remove(serviceDelivery);
        serviceDelivery.setServiceRecord(null);
        return this;
    }

    public void setServiceDeliveries(Set<ServiceDelivery> serviceDeliveries) {
        this.serviceDeliveries = serviceDeliveries;
    }

    public Set<ServiceDeliveryForm> getServiceDeliveryForms() {
        return serviceDeliveryForms;
    }

    public ServiceRecord serviceDeliveryForms(Set<ServiceDeliveryForm> serviceDeliveryForms) {
        this.serviceDeliveryForms = serviceDeliveryForms;
        return this;
    }

    public ServiceRecord addServiceDeliveryForm(ServiceDeliveryForm serviceDeliveryForm) {
        this.serviceDeliveryForms.add(serviceDeliveryForm);
        serviceDeliveryForm.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceDeliveryForm(ServiceDeliveryForm serviceDeliveryForm) {
        this.serviceDeliveryForms.remove(serviceDeliveryForm);
        serviceDeliveryForm.setServiceRecord(null);
        return this;
    }

    public void setServiceDeliveryForms(Set<ServiceDeliveryForm> serviceDeliveryForms) {
        this.serviceDeliveryForms = serviceDeliveryForms;
    }

    public Set<ServiceDescription> getServiceDescriptions() {
        return serviceDescriptions;
    }

    public ServiceRecord serviceDescriptions(Set<ServiceDescription> serviceDescriptions) {
        this.serviceDescriptions = serviceDescriptions;
        return this;
    }

    public ServiceRecord addServiceDescription(ServiceDescription serviceDescription) {
        this.serviceDescriptions.add(serviceDescription);
        serviceDescription.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceDescription(ServiceDescription serviceDescription) {
        this.serviceDescriptions.remove(serviceDescription);
        serviceDescription.setServiceRecord(null);
        return this;
    }

    public void setServiceDescriptions(Set<ServiceDescription> serviceDescriptions) {
        this.serviceDescriptions = serviceDescriptions;
    }

    public Set<ServiceEvent> getServiceEvents() {
        return serviceEvents;
    }

    public ServiceRecord serviceEvents(Set<ServiceEvent> serviceEvents) {
        this.serviceEvents = serviceEvents;
        return this;
    }

    public ServiceRecord addServiceEvent(ServiceEvent serviceEvent) {
        this.serviceEvents.add(serviceEvent);
        serviceEvent.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceEvent(ServiceEvent serviceEvent) {
        this.serviceEvents.remove(serviceEvent);
        serviceEvent.setServiceRecord(null);
        return this;
    }

    public void setServiceEvents(Set<ServiceEvent> serviceEvents) {
        this.serviceEvents = serviceEvents;
    }

    public Set<ServiceEvidence> getServiceEvidences() {
        return serviceEvidences;
    }

    public ServiceRecord serviceEvidences(Set<ServiceEvidence> serviceEvidences) {
        this.serviceEvidences = serviceEvidences;
        return this;
    }

    public ServiceRecord addServiceEvidence(ServiceEvidence serviceEvidence) {
        this.serviceEvidences.add(serviceEvidence);
        serviceEvidence.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceEvidence(ServiceEvidence serviceEvidence) {
        this.serviceEvidences.remove(serviceEvidence);
        serviceEvidence.setServiceRecord(null);
        return this;
    }

    public void setServiceEvidences(Set<ServiceEvidence> serviceEvidences) {
        this.serviceEvidences = serviceEvidences;
    }

    public Set<ServiceGroup> getServiceGroups() {
        return serviceGroups;
    }

    public ServiceRecord serviceGroups(Set<ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
        return this;
    }

    public ServiceRecord addServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroups.add(serviceGroup);
        serviceGroup.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceGroup(ServiceGroup serviceGroup) {
        this.serviceGroups.remove(serviceGroup);
        serviceGroup.setServiceRecord(null);
        return this;
    }

    public void setServiceGroups(Set<ServiceGroup> serviceGroups) {
        this.serviceGroups = serviceGroups;
    }

    public Set<ServiceName> getServiceNames() {
        return serviceNames;
    }

    public ServiceRecord serviceNames(Set<ServiceName> serviceNames) {
        this.serviceNames = serviceNames;
        return this;
    }

    public ServiceRecord addServiceName(ServiceName serviceName) {
        this.serviceNames.add(serviceName);
        serviceName.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceName(ServiceName serviceName) {
        this.serviceNames.remove(serviceName);
        serviceName.setServiceRecord(null);
        return this;
    }

    public void setServiceNames(Set<ServiceName> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public Set<ServiceRelationship> getFromServices() {
        return fromServices;
    }

    public ServiceRecord fromServices(Set<ServiceRelationship> serviceRelationships) {
        this.fromServices = serviceRelationships;
        return this;
    }

    public ServiceRecord addFromService(ServiceRelationship serviceRelationship) {
        this.fromServices.add(serviceRelationship);
        serviceRelationship.setFromService(this);
        return this;
    }

    public ServiceRecord removeFromService(ServiceRelationship serviceRelationship) {
        this.fromServices.remove(serviceRelationship);
        serviceRelationship.setFromService(null);
        return this;
    }

    public void setFromServices(Set<ServiceRelationship> serviceRelationships) {
        this.fromServices = serviceRelationships;
    }

    public Set<ServiceRelationship> getToServices() {
        return toServices;
    }

    public ServiceRecord toServices(Set<ServiceRelationship> serviceRelationships) {
        this.toServices = serviceRelationships;
        return this;
    }

    public ServiceRecord addToService(ServiceRelationship serviceRelationship) {
        this.toServices.add(serviceRelationship);
        serviceRelationship.setToService(this);
        return this;
    }

    public ServiceRecord removeToService(ServiceRelationship serviceRelationship) {
        this.toServices.remove(serviceRelationship);
        serviceRelationship.setToService(null);
        return this;
    }

    public void setToServices(Set<ServiceRelationship> serviceRelationships) {
        this.toServices = serviceRelationships;
    }

    public Set<ServiceSupportRole> getServiceSupportRoles() {
        return serviceSupportRoles;
    }

    public ServiceRecord serviceSupportRoles(Set<ServiceSupportRole> serviceSupportRoles) {
        this.serviceSupportRoles = serviceSupportRoles;
        return this;
    }

    public ServiceRecord addServiceSupportRole(ServiceSupportRole serviceSupportRole) {
        this.serviceSupportRoles.add(serviceSupportRole);
        serviceSupportRole.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceSupportRole(ServiceSupportRole serviceSupportRole) {
        this.serviceSupportRoles.remove(serviceSupportRole);
        serviceSupportRole.setServiceRecord(null);
        return this;
    }

    public void setServiceSupportRoles(Set<ServiceSupportRole> serviceSupportRoles) {
        this.serviceSupportRoles = serviceSupportRoles;
    }

    public Set<ServiceTagItems> getServiceTagItems() {
        return serviceTagItems;
    }

    public ServiceRecord serviceTagItems(Set<ServiceTagItems> serviceTagItems) {
        this.serviceTagItems = serviceTagItems;
        return this;
    }

    public ServiceRecord addServiceTagItems(ServiceTagItems serviceTagItems) {
        this.serviceTagItems.add(serviceTagItems);
        serviceTagItems.setServiceRecord(this);
        return this;
    }

    public ServiceRecord removeServiceTagItems(ServiceTagItems serviceTagItems) {
        this.serviceTagItems.remove(serviceTagItems);
        serviceTagItems.setServiceRecord(null);
        return this;
    }

    public void setServiceTagItems(Set<ServiceTagItems> serviceTagItems) {
        this.serviceTagItems = serviceTagItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceRecord)) {
            return false;
        }
        return id != null && id.equals(((ServiceRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceRecord{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDateTime='" + getCreatedDateTime() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDateTime='" + getModifiedDateTime() + "'" +
            ", version=" + getVersion() +
            ", active='" + getActive() + "'" +
            ", eligibility='" + getEligibility() + "'" +
            ", fees='" + getFees() + "'" +
            ", groupHeader='" + getGroupHeader() + "'" +
            ", groupId='" + getGroupId() + "'" +
            ", interactionId='" + getInteractionId() + "'" +
            ", keywords='" + getKeywords() + "'" +
            ", preRequisites='" + getPreRequisites() + "'" +
            ", qgsServiceId='" + getQgsServiceId() + "'" +
            ", referenceUrl='" + getReferenceUrl() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            ", validatedDate='" + getValidatedDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", preRequisitesNew='" + getPreRequisitesNew() + "'" +
            ", referenceUrlNew='" + getReferenceUrlNew() + "'" +
            ", eligibilityNew='" + getEligibilityNew() + "'" +
            ", serviceContext='" + getServiceContext() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", roadmapLoginRequired='" + getRoadmapLoginRequired() + "'" +
            ", roadmapCustomerIdRequired='" + getRoadmapCustomerIdRequired() + "'" +
            ", roadmapCustomerDetails='" + getRoadmapCustomerDetails() + "'" +
            ", roadmapImproveIntention='" + getRoadmapImproveIntention() + "'" +
            ", roadmapImproveFuture='" + getRoadmapImproveFuture() + "'" +
            ", roadmapImproveType='" + getRoadmapImproveType() + "'" +
            ", roadmapImproveWhen='" + getRoadmapImproveWhen() + "'" +
            ", roadmapImproveHow='" + getRoadmapImproveHow() + "'" +
            ", roadmapMaturityCurrent='" + getRoadmapMaturityCurrent() + "'" +
            ", roadmapMaturityDesired='" + getRoadmapMaturityDesired() + "'" +
            ", roadmapComments='" + getRoadmapComments() + "'" +
            ", howTo='" + getHowTo() + "'" +
            "}";
    }
}
