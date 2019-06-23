import { Moment } from 'moment';
import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';

export interface IAgencySupportRoleContextType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  context?: string;
  agencySupportRoles?: IAgencySupportRole[];
}

export class AgencySupportRoleContextType implements IAgencySupportRoleContextType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public context?: string,
    public agencySupportRoles?: IAgencySupportRole[]
  ) {}
}
