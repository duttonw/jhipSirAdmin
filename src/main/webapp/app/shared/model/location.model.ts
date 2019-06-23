import { Moment } from 'moment';
import { IDeliveryChannel } from 'app/shared/model/delivery-channel.model';
import { ILocationAddress } from 'app/shared/model/location-address.model';
import { ILocationEmail } from 'app/shared/model/location-email.model';
import { ILocationPhone } from 'app/shared/model/location-phone.model';

export interface ILocation {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  accessibilityFacilities?: string;
  additionalInformation?: string;
  locationName?: string;
  agencyId?: number;
  locationHoursId?: number;
  locationTypeId?: number;
  deliveryChannels?: IDeliveryChannel[];
  locationAddresses?: ILocationAddress[];
  locationEmails?: ILocationEmail[];
  locationPhones?: ILocationPhone[];
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public accessibilityFacilities?: string,
    public additionalInformation?: string,
    public locationName?: string,
    public agencyId?: number,
    public locationHoursId?: number,
    public locationTypeId?: number,
    public deliveryChannels?: IDeliveryChannel[],
    public locationAddresses?: ILocationAddress[],
    public locationEmails?: ILocationEmail[],
    public locationPhones?: ILocationPhone[]
  ) {}
}
