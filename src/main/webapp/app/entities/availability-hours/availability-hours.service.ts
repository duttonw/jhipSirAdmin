import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';

type EntityResponseType = HttpResponse<IAvailabilityHours>;
type EntityArrayResponseType = HttpResponse<IAvailabilityHours[]>;

@Injectable({ providedIn: 'root' })
export class AvailabilityHoursService {
  public resourceUrl = SERVER_API_URL + 'api/availability-hours';

  constructor(protected http: HttpClient) {}

  create(availabilityHours: IAvailabilityHours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(availabilityHours);
    return this.http
      .post<IAvailabilityHours>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(availabilityHours: IAvailabilityHours): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(availabilityHours);
    return this.http
      .put<IAvailabilityHours>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAvailabilityHours>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAvailabilityHours[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(availabilityHours: IAvailabilityHours): IAvailabilityHours {
    const copy: IAvailabilityHours = Object.assign({}, availabilityHours, {
      createdDateTime:
        availabilityHours.createdDateTime != null && availabilityHours.createdDateTime.isValid()
          ? availabilityHours.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        availabilityHours.modifiedDateTime != null && availabilityHours.modifiedDateTime.isValid()
          ? availabilityHours.modifiedDateTime.toJSON()
          : null,
      validFrom: availabilityHours.validFrom != null && availabilityHours.validFrom.isValid() ? availabilityHours.validFrom.toJSON() : null,
      validTo: availabilityHours.validTo != null && availabilityHours.validTo.isValid() ? availabilityHours.validTo.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDateTime = res.body.createdDateTime != null ? moment(res.body.createdDateTime) : null;
      res.body.modifiedDateTime = res.body.modifiedDateTime != null ? moment(res.body.modifiedDateTime) : null;
      res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((availabilityHours: IAvailabilityHours) => {
        availabilityHours.createdDateTime = availabilityHours.createdDateTime != null ? moment(availabilityHours.createdDateTime) : null;
        availabilityHours.modifiedDateTime = availabilityHours.modifiedDateTime != null ? moment(availabilityHours.modifiedDateTime) : null;
        availabilityHours.validFrom = availabilityHours.validFrom != null ? moment(availabilityHours.validFrom) : null;
        availabilityHours.validTo = availabilityHours.validTo != null ? moment(availabilityHours.validTo) : null;
      });
    }
    return res;
  }
}
