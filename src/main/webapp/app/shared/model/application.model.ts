import { Moment } from 'moment';
import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';

export interface IApplication {
  id?: number;
  contactEmail?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  name?: string;
  migratedBy?: string;
  version?: number;
  applicationServiceOverrides?: IApplicationServiceOverride[];
}

export class Application implements IApplication {
  constructor(
    public id?: number,
    public contactEmail?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public name?: string,
    public migratedBy?: string,
    public version?: number,
    public applicationServiceOverrides?: IApplicationServiceOverride[]
  ) {}
}
