import { Moment } from 'moment';

export interface IServiceEvidence {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  evidenceName?: string;
  metaData?: string;
  migrated?: string;
  migratedBy?: string;
  displayedForCategoryCategory?: string;
  displayedForCategoryId?: number;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
}

export class ServiceEvidence implements IServiceEvidence {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public evidenceName?: string,
    public metaData?: string,
    public migrated?: string,
    public migratedBy?: string,
    public displayedForCategoryCategory?: string,
    public displayedForCategoryId?: number,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number
  ) {}
}
