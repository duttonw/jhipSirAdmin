import { Moment } from 'moment';

export interface IServiceRelationship {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  relationship?: string;
  fromServiceId?: number;
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
    public fromServiceId?: number,
    public toServiceId?: number
  ) {}
}
