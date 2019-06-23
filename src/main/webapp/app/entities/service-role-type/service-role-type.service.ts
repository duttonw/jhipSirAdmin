import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceRoleType } from 'app/shared/model/service-role-type.model';

type EntityResponseType = HttpResponse<IServiceRoleType>;
type EntityArrayResponseType = HttpResponse<IServiceRoleType[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRoleTypeService {
  public resourceUrl = SERVER_API_URL + 'api/service-role-types';

  constructor(protected http: HttpClient) {}

  create(serviceRoleType: IServiceRoleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRoleType);
    return this.http
      .post<IServiceRoleType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceRoleType: IServiceRoleType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRoleType);
    return this.http
      .put<IServiceRoleType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceRoleType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceRoleType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceRoleType: IServiceRoleType): IServiceRoleType {
    const copy: IServiceRoleType = Object.assign({}, serviceRoleType, {
      createdDateTime:
        serviceRoleType.createdDateTime != null && serviceRoleType.createdDateTime.isValid()
          ? serviceRoleType.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceRoleType.modifiedDateTime != null && serviceRoleType.modifiedDateTime.isValid()
          ? serviceRoleType.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceRoleType: IServiceRoleType) => {
        serviceRoleType.createdDateTime = serviceRoleType.createdDateTime != null ? moment(serviceRoleType.createdDateTime) : null;
        serviceRoleType.modifiedDateTime = serviceRoleType.modifiedDateTime != null ? moment(serviceRoleType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
