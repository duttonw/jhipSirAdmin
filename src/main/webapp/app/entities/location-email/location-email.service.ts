import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocationEmail } from 'app/shared/model/location-email.model';

type EntityResponseType = HttpResponse<ILocationEmail>;
type EntityArrayResponseType = HttpResponse<ILocationEmail[]>;

@Injectable({ providedIn: 'root' })
export class LocationEmailService {
  public resourceUrl = SERVER_API_URL + 'api/location-emails';

  constructor(protected http: HttpClient) {}

  create(locationEmail: ILocationEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationEmail);
    return this.http
      .post<ILocationEmail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(locationEmail: ILocationEmail): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationEmail);
    return this.http
      .put<ILocationEmail>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocationEmail>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocationEmail[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(locationEmail: ILocationEmail): ILocationEmail {
    const copy: ILocationEmail = Object.assign({}, locationEmail, {
      createdDateTime:
        locationEmail.createdDateTime != null && locationEmail.createdDateTime.isValid() ? locationEmail.createdDateTime.toJSON() : null,
      modifiedDateTime:
        locationEmail.modifiedDateTime != null && locationEmail.modifiedDateTime.isValid() ? locationEmail.modifiedDateTime.toJSON() : null
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
      res.body.forEach((locationEmail: ILocationEmail) => {
        locationEmail.createdDateTime = locationEmail.createdDateTime != null ? moment(locationEmail.createdDateTime) : null;
        locationEmail.modifiedDateTime = locationEmail.modifiedDateTime != null ? moment(locationEmail.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
