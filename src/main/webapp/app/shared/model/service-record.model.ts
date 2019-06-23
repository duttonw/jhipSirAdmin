import { Moment } from 'moment';
import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';
import { IIntegrationMapping } from 'app/shared/model/integration-mapping.model';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { IServiceDelivery } from 'app/shared/model/service-delivery.model';
import { IServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';
import { IServiceDescription } from 'app/shared/model/service-description.model';
import { IServiceEvent } from 'app/shared/model/service-event.model';
import { IServiceEvidence } from 'app/shared/model/service-evidence.model';
import { IServiceGroup } from 'app/shared/model/service-group.model';
import { IServiceName } from 'app/shared/model/service-name.model';
import { IServiceRelationship } from 'app/shared/model/service-relationship.model';
import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';
import { IServiceTagItems } from 'app/shared/model/service-tag-items.model';

export interface IServiceRecord {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  active?: string;
  eligibility?: string;
  fees?: string;
  groupHeader?: string;
  groupId?: string;
  interactionId?: string;
  keywords?: string;
  preRequisites?: string;
  qgsServiceId?: string;
  referenceUrl?: string;
  serviceName?: string;
  validatedDate?: Moment;
  description?: string;
  preRequisitesNew?: string;
  referenceUrlNew?: string;
  eligibilityNew?: string;
  serviceContext?: string;
  longDescription?: string;
  name?: string;
  startDate?: Moment;
  endDate?: Moment;
  roadmapLoginRequired?: string;
  roadmapCustomerIdRequired?: string;
  roadmapCustomerDetails?: string;
  roadmapImproveIntention?: string;
  roadmapImproveFuture?: string;
  roadmapImproveType?: string;
  roadmapImproveWhen?: string;
  roadmapImproveHow?: string;
  roadmapMaturityCurrent?: string;
  roadmapMaturityDesired?: string;
  roadmapComments?: string;
  howTo?: string;
  deliveryOrgBusinessUnitName?: string;
  deliveryOrgId?: number;
  parentServiceServiceName?: string;
  parentServiceId?: number;
  serviceFranchiseFranchiseName?: string;
  serviceFranchiseId?: number;
  applicationServiceOverrides?: IApplicationServiceOverride[];
  integrationMappings?: IIntegrationMapping[];
  serviceRecords?: IServiceRecord[];
  serviceDeliveries?: IServiceDelivery[];
  serviceDeliveryForms?: IServiceDeliveryForm[];
  serviceDescriptions?: IServiceDescription[];
  serviceEvents?: IServiceEvent[];
  serviceEvidences?: IServiceEvidence[];
  serviceGroups?: IServiceGroup[];
  serviceNames?: IServiceName[];
  fromServices?: IServiceRelationship[];
  toServices?: IServiceRelationship[];
  serviceSupportRoles?: IServiceSupportRole[];
  serviceTagItems?: IServiceTagItems[];
}

export class ServiceRecord implements IServiceRecord {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public active?: string,
    public eligibility?: string,
    public fees?: string,
    public groupHeader?: string,
    public groupId?: string,
    public interactionId?: string,
    public keywords?: string,
    public preRequisites?: string,
    public qgsServiceId?: string,
    public referenceUrl?: string,
    public serviceName?: string,
    public validatedDate?: Moment,
    public description?: string,
    public preRequisitesNew?: string,
    public referenceUrlNew?: string,
    public eligibilityNew?: string,
    public serviceContext?: string,
    public longDescription?: string,
    public name?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public roadmapLoginRequired?: string,
    public roadmapCustomerIdRequired?: string,
    public roadmapCustomerDetails?: string,
    public roadmapImproveIntention?: string,
    public roadmapImproveFuture?: string,
    public roadmapImproveType?: string,
    public roadmapImproveWhen?: string,
    public roadmapImproveHow?: string,
    public roadmapMaturityCurrent?: string,
    public roadmapMaturityDesired?: string,
    public roadmapComments?: string,
    public howTo?: string,
    public deliveryOrgBusinessUnitName?: string,
    public deliveryOrgId?: number,
    public parentServiceServiceName?: string,
    public parentServiceId?: number,
    public serviceFranchiseFranchiseName?: string,
    public serviceFranchiseId?: number,
    public applicationServiceOverrides?: IApplicationServiceOverride[],
    public integrationMappings?: IIntegrationMapping[],
    public serviceRecords?: IServiceRecord[],
    public serviceDeliveries?: IServiceDelivery[],
    public serviceDeliveryForms?: IServiceDeliveryForm[],
    public serviceDescriptions?: IServiceDescription[],
    public serviceEvents?: IServiceEvent[],
    public serviceEvidences?: IServiceEvidence[],
    public serviceGroups?: IServiceGroup[],
    public serviceNames?: IServiceName[],
    public fromServices?: IServiceRelationship[],
    public toServices?: IServiceRelationship[],
    public serviceSupportRoles?: IServiceSupportRole[],
    public serviceTagItems?: IServiceTagItems[]
  ) {}
}
