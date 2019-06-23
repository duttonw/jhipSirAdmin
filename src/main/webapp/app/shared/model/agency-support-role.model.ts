import { Moment } from 'moment';

export interface IAgencySupportRole {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  contactEmail?: string;
  agencyAgencyCode?: string;
  agencyId?: number;
  agencyRoleTypeServiceRole?: string;
  agencyRoleTypeId?: number;
  agencySupportContextTypeContext?: string;
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
    public agencyAgencyCode?: string,
    public agencyId?: number,
    public agencyRoleTypeServiceRole?: string,
    public agencyRoleTypeId?: number,
    public agencySupportContextTypeContext?: string,
    public agencySupportContextTypeId?: number
  ) {}
}
