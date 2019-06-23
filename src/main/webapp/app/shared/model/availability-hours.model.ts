import { Moment } from 'moment';
import { IDeliveryChannel } from 'app/shared/model/delivery-channel.model';
import { ILocation } from 'app/shared/model/location.model';
import { IOpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';

export interface IAvailabilityHours {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  available?: string;
  availabilityHoursId?: number;
  comments?: string;
  validFrom?: Moment;
  validTo?: Moment;
  deliveryChannels?: IDeliveryChannel[];
  locations?: ILocation[];
  openingHoursSpecifications?: IOpeningHoursSpecification[];
}

export class AvailabilityHours implements IAvailabilityHours {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public available?: string,
    public availabilityHoursId?: number,
    public comments?: string,
    public validFrom?: Moment,
    public validTo?: Moment,
    public deliveryChannels?: IDeliveryChannel[],
    public locations?: ILocation[],
    public openingHoursSpecifications?: IOpeningHoursSpecification[]
  ) {}
}
