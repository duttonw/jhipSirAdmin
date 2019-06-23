import { Moment } from 'moment';
import { IApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

export interface IApplicationServiceOverrideTag {
  id?: number;
  name?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  migratedBy?: string;
  version?: number;
  parentId?: number;
  applicationServiceOverrideTagItems?: IApplicationServiceOverrideTagItems[];
}

export class ApplicationServiceOverrideTag implements IApplicationServiceOverrideTag {
  constructor(
    public id?: number,
    public name?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public migratedBy?: string,
    public version?: number,
    public parentId?: number,
    public applicationServiceOverrideTagItems?: IApplicationServiceOverrideTagItems[]
  ) {}
}
