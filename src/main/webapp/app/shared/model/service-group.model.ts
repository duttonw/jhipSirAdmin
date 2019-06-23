import { Moment } from 'moment';

export interface IServiceGroup {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  migrated?: string;
  migratedBy?: string;
  serviceGroupCategoryId?: number;
  serviceGroupCategoryTypeId?: number;
  serviceRecordId?: number;
}

export class ServiceGroup implements IServiceGroup {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public migrated?: string,
    public migratedBy?: string,
    public serviceGroupCategoryId?: number,
    public serviceGroupCategoryTypeId?: number,
    public serviceRecordId?: number
  ) {}
}
