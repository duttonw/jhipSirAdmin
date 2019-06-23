import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';

type EntityResponseType = HttpResponse<IApplicationServiceOverrideAttribute>;
type EntityArrayResponseType = HttpResponse<IApplicationServiceOverrideAttribute[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideAttributeService {
  public resourceUrl = SERVER_API_URL + 'api/application-service-override-attributes';

  constructor(protected http: HttpClient) {}

  create(applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverrideAttribute);
    return this.http
      .post<IApplicationServiceOverrideAttribute>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverrideAttribute);
    return this.http
      .put<IApplicationServiceOverrideAttribute>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationServiceOverrideAttribute>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationServiceOverrideAttribute[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(
    applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute
  ): IApplicationServiceOverrideAttribute {
    const copy: IApplicationServiceOverrideAttribute = Object.assign({}, applicationServiceOverrideAttribute, {
      createdDateTime:
        applicationServiceOverrideAttribute.createdDateTime != null && applicationServiceOverrideAttribute.createdDateTime.isValid()
          ? applicationServiceOverrideAttribute.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        applicationServiceOverrideAttribute.modifiedDateTime != null && applicationServiceOverrideAttribute.modifiedDateTime.isValid()
          ? applicationServiceOverrideAttribute.modifiedDateTime.toJSON()
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
      res.body.forEach((applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute) => {
        applicationServiceOverrideAttribute.createdDateTime =
          applicationServiceOverrideAttribute.createdDateTime != null ? moment(applicationServiceOverrideAttribute.createdDateTime) : null;
        applicationServiceOverrideAttribute.modifiedDateTime =
          applicationServiceOverrideAttribute.modifiedDateTime != null
            ? moment(applicationServiceOverrideAttribute.modifiedDateTime)
            : null;
      });
    }
    return res;
  }
}
