import { Moment } from 'moment';
import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';

export interface IServiceSupportRoleContextType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  context?: string;
  serviceSupportRoles?: IServiceSupportRole[];
}

export class ServiceSupportRoleContextType implements IServiceSupportRoleContextType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public context?: string,
    public serviceSupportRoles?: IServiceSupportRole[]
  ) {}
}
