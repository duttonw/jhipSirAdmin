import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocation } from 'app/shared/model/location.model';

type EntityResponseType = HttpResponse<ILocation>;
type EntityArrayResponseType = HttpResponse<ILocation[]>;

@Injectable({ providedIn: 'root' })
export class LocationService {
  public resourceUrl = SERVER_API_URL + 'api/locations';

  constructor(protected http: HttpClient) {}

  create(location: ILocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(location);
    return this.http
      .post<ILocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(location: ILocation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(location);
    return this.http
      .put<ILocation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(location: ILocation): ILocation {
    const copy: ILocation = Object.assign({}, location, {
      createdDateTime: location.createdDateTime != null && location.createdDateTime.isValid() ? location.createdDateTime.toJSON() : null,
      modifiedDateTime: location.modifiedDateTime != null && location.modifiedDateTime.isValid() ? location.modifiedDateTime.toJSON() : null
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
      res.body.forEach((location: ILocation) => {
        location.createdDateTime = location.createdDateTime != null ? moment(location.createdDateTime) : null;
        location.modifiedDateTime = location.modifiedDateTime != null ? moment(location.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
