import { Moment } from 'moment';

export interface ILocationAddress {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  additionalInformation?: string;
  addressLine1?: string;
  addressLine2?: string;
  addressType?: string;
  countryCode?: string;
  localityName?: string;
  locationPoint?: string;
  postcode?: string;
  stateCode?: string;
  locationId?: number;
}

export class LocationAddress implements ILocationAddress {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public additionalInformation?: string,
    public addressLine1?: string,
    public addressLine2?: string,
    public addressType?: string,
    public countryCode?: string,
    public localityName?: string,
    public locationPoint?: string,
    public postcode?: string,
    public stateCode?: string,
    public locationId?: number
  ) {}
}
