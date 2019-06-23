import { Moment } from 'moment';
import { IServiceTag } from 'app/shared/model/service-tag.model';
import { IServiceTagItems } from 'app/shared/model/service-tag-items.model';

export interface IServiceTag {
  id?: number;
  name?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  parentName?: string;
  parentId?: number;
  serviceTags?: IServiceTag[];
  serviceTagItems?: IServiceTagItems[];
}

export class ServiceTag implements IServiceTag {
  constructor(
    public id?: number,
    public name?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public parentName?: string,
    public parentId?: number,
    public serviceTags?: IServiceTag[],
    public serviceTagItems?: IServiceTagItems[]
  ) {}
}
