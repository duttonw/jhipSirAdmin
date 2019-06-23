import { Moment } from 'moment';

export interface IApplicationServiceOverrideAttribute {
  id?: number;
  name?: string;
  value?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  migratedBy?: string;
  version?: number;
  applicationServiceOverrideName?: string;
  applicationServiceOverrideId?: number;
}

export class ApplicationServiceOverrideAttribute implements IApplicationServiceOverrideAttribute {
  constructor(
    public id?: number,
    public name?: string,
    public value?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public migratedBy?: string,
    public version?: number,
    public applicationServiceOverrideName?: string,
    public applicationServiceOverrideId?: number
  ) {}
}
