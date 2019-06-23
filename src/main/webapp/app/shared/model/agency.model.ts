import { Moment } from 'moment';
import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';
import { IIntegrationMapping } from 'app/shared/model/integration-mapping.model';
import { ILocation } from 'app/shared/model/location.model';
import { IServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';

export interface IAgency {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  agencyCode?: string;
  agencyName?: string;
  agencyUrl?: string;
  agencyTypeAgencyTypeName?: string;
  agencyTypeId?: number;
  agencySupportRoles?: IAgencySupportRole[];
  integrationMappings?: IIntegrationMapping[];
  locations?: ILocation[];
  serviceDeliveryOrganisations?: IServiceDeliveryOrganisation[];
}

export class Agency implements IAgency {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public agencyCode?: string,
    public agencyName?: string,
    public agencyUrl?: string,
    public agencyTypeAgencyTypeName?: string,
    public agencyTypeId?: number,
    public agencySupportRoles?: IAgencySupportRole[],
    public integrationMappings?: IIntegrationMapping[],
    public locations?: ILocation[],
    public serviceDeliveryOrganisations?: IServiceDeliveryOrganisation[]
  ) {}
}
