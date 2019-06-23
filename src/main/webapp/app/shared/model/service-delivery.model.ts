import { Moment } from 'moment';
import { IDeliveryChannel } from 'app/shared/model/delivery-channel.model';

export interface IServiceDelivery {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  serviceDeliveryChannelType?: string;
  status?: string;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
  deliveryChannels?: IDeliveryChannel[];
}

export class ServiceDelivery implements IServiceDelivery {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public serviceDeliveryChannelType?: string,
    public status?: string,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number,
    public deliveryChannels?: IDeliveryChannel[]
  ) {}
}
