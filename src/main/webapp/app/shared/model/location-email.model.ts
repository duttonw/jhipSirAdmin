import { Moment } from 'moment';

export interface ILocationEmail {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  comment?: string;
  email?: string;
  locationId?: number;
}

export class LocationEmail implements ILocationEmail {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public comment?: string,
    public email?: string,
    public locationId?: number
  ) {}
}
