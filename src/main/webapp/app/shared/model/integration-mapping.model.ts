import { Moment } from 'moment';

export interface IIntegrationMapping {
  id?: number;
  agencyServiceId?: string;
  serviceName?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  agencyAgencyCode?: string;
  agencyId?: number;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
}

export class IntegrationMapping implements IIntegrationMapping {
  constructor(
    public id?: number,
    public agencyServiceId?: string,
    public serviceName?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public agencyAgencyCode?: string,
    public agencyId?: number,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number
  ) {}
}
