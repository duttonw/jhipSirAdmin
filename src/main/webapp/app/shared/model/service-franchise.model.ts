import { Moment } from 'moment';
import { IServiceRecord } from 'app/shared/model/service-record.model';

export interface IServiceFranchise {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  franchiseName?: string;
  version?: number;
  serviceRecords?: IServiceRecord[];
}

export class ServiceFranchise implements IServiceFranchise {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public franchiseName?: string,
    public version?: number,
    public serviceRecords?: IServiceRecord[]
  ) {}
}
