import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocationPhone } from 'app/shared/model/location-phone.model';

type EntityResponseType = HttpResponse<ILocationPhone>;
type EntityArrayResponseType = HttpResponse<ILocationPhone[]>;

@Injectable({ providedIn: 'root' })
export class LocationPhoneService {
  public resourceUrl = SERVER_API_URL + 'api/location-phones';

  constructor(protected http: HttpClient) {}

  create(locationPhone: ILocationPhone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationPhone);
    return this.http
      .post<ILocationPhone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(locationPhone: ILocationPhone): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationPhone);
    return this.http
      .put<ILocationPhone>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocationPhone>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocationPhone[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(locationPhone: ILocationPhone): ILocationPhone {
    const copy: ILocationPhone = Object.assign({}, locationPhone, {
      createdDateTime:
        locationPhone.createdDateTime != null && locationPhone.createdDateTime.isValid() ? locationPhone.createdDateTime.toJSON() : null,
      modifiedDateTime:
        locationPhone.modifiedDateTime != null && locationPhone.modifiedDateTime.isValid() ? locationPhone.modifiedDateTime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDateTime = res.body.createdDateTime != null ? moment(res.body.createdDateTime) : null;
      res.body.modifiedDateTime = res.body.modifiedDateTime != null ? moment(res.body.modifiedDateTime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((locationPhone: ILocationPhone) => {
        locationPhone.createdDateTime = locationPhone.createdDateTime != null ? moment(locationPhone.createdDateTime) : null;
        locationPhone.modifiedDateTime = locationPhone.modifiedDateTime != null ? moment(locationPhone.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
