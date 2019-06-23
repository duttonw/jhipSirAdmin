import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocationType } from 'app/shared/model/location-type.model';

type EntityResponseType = HttpResponse<ILocationType>;
type EntityArrayResponseType = HttpResponse<ILocationType[]>;

@Injectable({ providedIn: 'root' })
export class LocationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/location-types';

  constructor(protected http: HttpClient) {}

  create(locationType: ILocationType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationType);
    return this.http
      .post<ILocationType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(locationType: ILocationType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationType);
    return this.http
      .put<ILocationType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocationType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocationType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(locationType: ILocationType): ILocationType {
    const copy: ILocationType = Object.assign({}, locationType, {
      createdDateTime:
        locationType.createdDateTime != null && locationType.createdDateTime.isValid() ? locationType.createdDateTime.toJSON() : null,
      modifiedDateTime:
        locationType.modifiedDateTime != null && locationType.modifiedDateTime.isValid() ? locationType.modifiedDateTime.toJSON() : null
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
      res.body.forEach((locationType: ILocationType) => {
        locationType.createdDateTime = locationType.createdDateTime != null ? moment(locationType.createdDateTime) : null;
        locationType.modifiedDateTime = locationType.modifiedDateTime != null ? moment(locationType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
