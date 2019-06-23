import { Moment } from 'moment';

export interface ILocationPhone {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  comment?: string;
  phoneNumber?: string;
  locationId?: number;
}

export class LocationPhone implements ILocationPhone {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public comment?: string,
    public phoneNumber?: string,
    public locationId?: number
  ) {}
}
