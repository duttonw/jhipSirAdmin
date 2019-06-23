import { Moment } from 'moment';

export interface IServiceSupportRole {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  contactEmail?: string;
  contactName?: string;
  contactPhoneNumber?: string;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
  serviceRoleTypeServiceRole?: string;
  serviceRoleTypeId?: number;
  serviceSupportContextTypeContext?: string;
  serviceSupportContextTypeId?: number;
}

export class ServiceSupportRole implements IServiceSupportRole {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public contactEmail?: string,
    public contactName?: string,
    public contactPhoneNumber?: string,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number,
    public serviceRoleTypeServiceRole?: string,
    public serviceRoleTypeId?: number,
    public serviceSupportContextTypeContext?: string,
    public serviceSupportContextTypeId?: number
  ) {}
}
