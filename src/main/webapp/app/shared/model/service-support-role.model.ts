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
  serviceRecordId?: number;
  serviceRoleTypeId?: number;
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
    public serviceRecordId?: number,
    public serviceRoleTypeId?: number,
    public serviceSupportContextTypeId?: number
  ) {}
}
