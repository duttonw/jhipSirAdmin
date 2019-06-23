import { Moment } from 'moment';

export interface IApplicationServiceOverrideTagItems {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  migratedBy?: string;
  version?: number;
  applicationServiceOverrideId?: number;
  applicationServiceOverrideTagId?: number;
}

export class ApplicationServiceOverrideTagItems implements IApplicationServiceOverrideTagItems {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public migratedBy?: string,
    public version?: number,
    public applicationServiceOverrideId?: number,
    public applicationServiceOverrideTagId?: number
  ) {}
}
