import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';

type EntityResponseType = HttpResponse<IServiceSupportRoleContextType>;
type EntityArrayResponseType = HttpResponse<IServiceSupportRoleContextType[]>;

@Injectable({ providedIn: 'root' })
export class ServiceSupportRoleContextTypeService {
  public resourceUrl = SERVER_API_URL + 'api/service-support-role-context-types';

  constructor(protected http: HttpClient) {}

  create(serviceSupportRoleContextType: IServiceSupportRoleContextType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceSupportRoleContextType);
    return this.http
      .post<IServiceSupportRoleContextType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceSupportRoleContextType: IServiceSupportRoleContextType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceSupportRoleContextType);
    return this.http
      .put<IServiceSupportRoleContextType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceSupportRoleContextType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceSupportRoleContextType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceSupportRoleContextType: IServiceSupportRoleContextType): IServiceSupportRoleContextType {
    const copy: IServiceSupportRoleContextType = Object.assign({}, serviceSupportRoleContextType, {
      createdDateTime:
        serviceSupportRoleContextType.createdDateTime != null && serviceSupportRoleContextType.createdDateTime.isValid()
          ? serviceSupportRoleContextType.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceSupportRoleContextType.modifiedDateTime != null && serviceSupportRoleContextType.modifiedDateTime.isValid()
          ? serviceSupportRoleContextType.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceSupportRoleContextType: IServiceSupportRoleContextType) => {
        serviceSupportRoleContextType.createdDateTime =
          serviceSupportRoleContextType.createdDateTime != null ? moment(serviceSupportRoleContextType.createdDateTime) : null;
        serviceSupportRoleContextType.modifiedDateTime =
          serviceSupportRoleContextType.modifiedDateTime != null ? moment(serviceSupportRoleContextType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
