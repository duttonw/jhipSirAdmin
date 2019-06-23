import { Moment } from 'moment';
import { IAgency } from 'app/shared/model/agency.model';

export interface IAgencyType {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  agencyTypeName?: string;
  agencies?: IAgency[];
}

export class AgencyType implements IAgencyType {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public agencyTypeName?: string,
    public agencies?: IAgency[]
  ) {}
}
