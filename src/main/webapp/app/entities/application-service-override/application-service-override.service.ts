import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';

type EntityResponseType = HttpResponse<IApplicationServiceOverride>;
type EntityArrayResponseType = HttpResponse<IApplicationServiceOverride[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideService {
  public resourceUrl = SERVER_API_URL + 'api/application-service-overrides';

  constructor(protected http: HttpClient) {}

  create(applicationServiceOverride: IApplicationServiceOverride): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverride);
    return this.http
      .post<IApplicationServiceOverride>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationServiceOverride: IApplicationServiceOverride): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverride);
    return this.http
      .put<IApplicationServiceOverride>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationServiceOverride>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationServiceOverride[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicationServiceOverride: IApplicationServiceOverride): IApplicationServiceOverride {
    const copy: IApplicationServiceOverride = Object.assign({}, applicationServiceOverride, {
      createdDateTime:
        applicationServiceOverride.createdDateTime != null && applicationServiceOverride.createdDateTime.isValid()
          ? applicationServiceOverride.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        applicationServiceOverride.modifiedDateTime != null && applicationServiceOverride.modifiedDateTime.isValid()
          ? applicationServiceOverride.modifiedDateTime.toJSON()
          : null
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
      res.body.forEach((applicationServiceOverride: IApplicationServiceOverride) => {
        applicationServiceOverride.createdDateTime =
          applicationServiceOverride.createdDateTime != null ? moment(applicationServiceOverride.createdDateTime) : null;
        applicationServiceOverride.modifiedDateTime =
          applicationServiceOverride.modifiedDateTime != null ? moment(applicationServiceOverride.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
