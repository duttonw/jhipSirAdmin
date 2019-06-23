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
  serviceGroupCategoryCategory?: string;
  serviceGroupCategoryId?: number;
  serviceGroupCategoryTypeCategoryType?: string;
  serviceGroupCategoryTypeId?: number;
  serviceRecordServiceName?: string;
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
    public serviceGroupCategoryCategory?: string,
    public serviceGroupCategoryId?: number,
    public serviceGroupCategoryTypeCategoryType?: string,
    public serviceGroupCategoryTypeId?: number,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number
  ) {}
}
