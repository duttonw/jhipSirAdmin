import { Moment } from 'moment';

export interface IServiceEvent {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  serviceEventSequence?: number;
  serviceRecordId?: number;
  serviceEventTypeId?: number;
}

export class ServiceEvent implements IServiceEvent {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public serviceEventSequence?: number,
    public serviceRecordId?: number,
    public serviceEventTypeId?: number
  ) {}
}
