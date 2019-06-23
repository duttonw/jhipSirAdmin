import { Moment } from 'moment';
import { IApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';
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
  parentName?: string;
  parentId?: number;
  applicationServiceOverrideTags?: IApplicationServiceOverrideTag[];
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
    public parentName?: string,
    public parentId?: number,
    public applicationServiceOverrideTags?: IApplicationServiceOverrideTag[],
    public applicationServiceOverrideTagItems?: IApplicationServiceOverrideTagItems[]
  ) {}
}
