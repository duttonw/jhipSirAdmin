import { Moment } from 'moment';

export interface IAgencySupportRole {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  contactEmail?: string;
  agencyId?: number;
  agencyRoleTypeId?: number;
  agencySupportContextTypeId?: number;
}

export class AgencySupportRole implements IAgencySupportRole {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public contactEmail?: string,
    public agencyId?: number,
    public agencyRoleTypeId?: number,
    public agencySupportContextTypeId?: number
  ) {}
}
