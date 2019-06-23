import { Moment } from 'moment';

export interface IServiceRelationship {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  relationship?: string;
  fromServiceServiceName?: string;
  fromServiceId?: number;
  toServiceServiceName?: string;
  toServiceId?: number;
}

export class ServiceRelationship implements IServiceRelationship {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public relationship?: string,
    public fromServiceServiceName?: string,
    public fromServiceId?: number,
    public toServiceServiceName?: string,
    public toServiceId?: number
  ) {}
}
