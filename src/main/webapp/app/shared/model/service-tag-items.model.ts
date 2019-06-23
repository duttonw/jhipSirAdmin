import { Moment } from 'moment';

export interface IServiceTagItems {
  id?: number;
  source?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  serviceRecordId?: number;
  serviceTagId?: number;
}

export class ServiceTagItems implements IServiceTagItems {
  constructor(
    public id?: number,
    public source?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public serviceRecordId?: number,
    public serviceTagId?: number
  ) {}
}
