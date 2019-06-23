import { Moment } from 'moment';

export interface IOpeningHoursSpecification {
  id?: number;
  createdBy?: string;
  createdDateTime?: Moment;
  modifiedBy?: string;
  modifiedDateTime?: Moment;
  version?: number;
  closes?: string;
  dayOfWeek?: string;
  opens?: string;
  validFrom?: Moment;
  validTo?: Moment;
  availabilityHoursId?: number;
}

export class OpeningHoursSpecification implements IOpeningHoursSpecification {
  constructor(
    public id?: number,
    public createdBy?: string,
    public createdDateTime?: Moment,
    public modifiedBy?: string,
    public modifiedDateTime?: Moment,
    public version?: number,
    public closes?: string,
    public dayOfWeek?: string,
    public opens?: string,
    public validFrom?: Moment,
    public validTo?: Moment,
    public availabilityHoursId?: number
  ) {}
}
