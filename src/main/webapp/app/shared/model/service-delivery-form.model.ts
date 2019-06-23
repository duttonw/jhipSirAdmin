import { Moment } from 'moment';

export interface IServiceDeliveryForm {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  formName?: string;
  formUrl?: string;
  source?: string;
  serviceRecordId?: number;
}

export class ServiceDeliveryForm implements IServiceDeliveryForm {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public formName?: string,
    public formUrl?: string,
    public source?: string,
    public serviceRecordId?: number
  ) {}
}
