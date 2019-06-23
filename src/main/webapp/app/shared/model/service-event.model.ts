import { Moment } from 'moment';

export interface IServiceEvent {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  serviceEventSequence?: number;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
  serviceEventTypeServiceEvent?: string;
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
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number,
    public serviceEventTypeServiceEvent?: string,
    public serviceEventTypeId?: number
  ) {}
}
