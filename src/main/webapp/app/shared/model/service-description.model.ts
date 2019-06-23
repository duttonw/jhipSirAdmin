import { Moment } from 'moment';

export interface IServiceDescription {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  context?: string;
  serviceDescription?: string;
  migrated?: string;
  migratedBy?: string;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
}

export class ServiceDescription implements IServiceDescription {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public context?: string,
    public serviceDescription?: string,
    public migrated?: string,
    public migratedBy?: string,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number
  ) {}
}
