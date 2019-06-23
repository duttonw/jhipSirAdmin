import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';

export interface ILocationType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  locationTypeCode?: string;
  locations?: ILocation[];
}

export class LocationType implements ILocationType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public locationTypeCode?: string,
    public locations?: ILocation[]
  ) {}
}
