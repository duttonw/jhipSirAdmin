import { Moment } from 'moment';

export interface IServiceName {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  context?: string;
  serviceName?: string;
  migrated?: string;
  migratedBy?: string;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
}

export class ServiceName implements IServiceName {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public context?: string,
    public serviceName?: string,
    public migrated?: string,
    public migratedBy?: string,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number
  ) {}
}
