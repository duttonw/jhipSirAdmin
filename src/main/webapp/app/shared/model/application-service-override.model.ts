import { Moment } from 'moment';
import { IApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';
import { IApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

export interface IApplicationServiceOverride {
  id?: number;
  description?: string;
  eligibility?: string;
  keywords?: string;
  longDescription?: string;
  name?: string;
  preRequisites?: string;
  fees?: string;
  active?: string;
  referenceUrl?: string;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  migratedBy?: string;
  version?: number;
  howTo?: string;
  serviceRecordServiceName?: string;
  serviceRecordId?: number;
  applicationName?: string;
  applicationId?: number;
  applicationServiceOverrideAttributes?: IApplicationServiceOverrideAttribute[];
  applicationServiceOverrideTagItems?: IApplicationServiceOverrideTagItems[];
}

export class ApplicationServiceOverride implements IApplicationServiceOverride {
  constructor(
    public id?: number,
    public description?: string,
    public eligibility?: string,
    public keywords?: string,
    public longDescription?: string,
    public name?: string,
    public preRequisites?: string,
    public fees?: string,
    public active?: string,
    public referenceUrl?: string,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public migratedBy?: string,
    public version?: number,
    public howTo?: string,
    public serviceRecordServiceName?: string,
    public serviceRecordId?: number,
    public applicationName?: string,
    public applicationId?: number,
    public applicationServiceOverrideAttributes?: IApplicationServiceOverrideAttribute[],
    public applicationServiceOverrideTagItems?: IApplicationServiceOverrideTagItems[]
  ) {}
}
