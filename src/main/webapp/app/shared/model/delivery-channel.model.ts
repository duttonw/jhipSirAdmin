import { Moment } from 'moment';

export interface IDeliveryChannel {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  additionalDetails?: string;
  deliveryChannelType?: string;
  deliveryChannelId?: number;
  virtualDeliveryDetails?: string;
  deliveryHoursId?: number;
  locationId?: number;
  serviceDeliveryId?: number;
}

export class DeliveryChannel implements IDeliveryChannel {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public additionalDetails?: string,
    public deliveryChannelType?: string,
    public deliveryChannelId?: number,
    public virtualDeliveryDetails?: string,
    public deliveryHoursId?: number,
    public locationId?: number,
    public serviceDeliveryId?: number
  ) {}
}
