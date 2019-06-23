import { Moment } from 'moment';
import { IServiceRecord } from 'app/shared/model/service-record.model';

export interface IServiceDeliveryOrganisation {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  businessUnitName?: string;
  agencyId?: number;
  serviceRecords?: IServiceRecord[];
}

export class ServiceDeliveryOrganisation implements IServiceDeliveryOrganisation {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public businessUnitName?: string,
    public agencyId?: number,
    public serviceRecords?: IServiceRecord[]
  ) {}
}
