import { Moment } from 'moment';
import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';
import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';

export interface IServiceRoleType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  serviceRole?: string;
  agencySupportRoles?: IAgencySupportRole[];
  serviceSupportRoles?: IServiceSupportRole[];
}

export class ServiceRoleType implements IServiceRoleType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public serviceRole?: string,
    public agencySupportRoles?: IAgencySupportRole[],
    public serviceSupportRoles?: IServiceSupportRole[]
  ) {}
}
