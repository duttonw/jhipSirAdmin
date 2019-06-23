import { Moment } from 'moment';
import { IServiceEvent } from 'app/shared/model/service-event.model';

export interface IServiceEventType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  serviceEvent?: string;
  serviceEvents?: IServiceEvent[];
}

export class ServiceEventType implements IServiceEventType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public serviceEvent?: string,
    public serviceEvents?: IServiceEvent[]
  ) {}
}
