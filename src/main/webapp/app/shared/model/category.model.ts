import { Moment } from 'moment';
import { IServiceEvidence } from 'app/shared/model/service-evidence.model';
import { IServiceGroup } from 'app/shared/model/service-group.model';

export interface ICategory {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  category?: string;
  migrated?: string;
  migratedBy?: string;
  serviceEvidences?: IServiceEvidence[];
  serviceGroups?: IServiceGroup[];
}

export class Category implements ICategory {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public category?: string,
    public migrated?: string,
    public migratedBy?: string,
    public serviceEvidences?: IServiceEvidence[],
    public serviceGroups?: IServiceGroup[]
  ) {}
}
