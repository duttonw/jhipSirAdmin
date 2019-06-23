import { Moment } from 'moment';
import { IServiceGroup } from 'app/shared/model/service-group.model';

export interface ICategoryType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  categoryType?: string;
  migrated?: string;
  migratedBy?: string;
  serviceGroups?: IServiceGroup[];
}

export class CategoryType implements ICategoryType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public categoryType?: string,
    public migrated?: string,
    public migratedBy?: string,
    public serviceGroups?: IServiceGroup[]
  ) {}
}
