import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';

type EntityResponseType = HttpResponse<IServiceSupportRole>;
type EntityArrayResponseType = HttpResponse<IServiceSupportRole[]>;

@Injectable({ providedIn: 'root' })
export class ServiceSupportRoleService {
  public resourceUrl = SERVER_API_URL + 'api/service-support-roles';

  constructor(protected http: HttpClient) {}

  create(serviceSupportRole: IServiceSupportRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceSupportRole);
    return this.http
      .post<IServiceSupportRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceSupportRole: IServiceSupportRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceSupportRole);
    return this.http
      .put<IServiceSupportRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceSupportRole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceSupportRole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceSupportRole: IServiceSupportRole): IServiceSupportRole {
    const copy: IServiceSupportRole = Object.assign({}, serviceSupportRole, {
      createdDateTime:
        serviceSupportRole.createdDateTime != null && serviceSupportRole.createdDateTime.isValid()
          ? serviceSupportRole.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceSupportRole.modifiedDateTime != null && serviceSupportRole.modifiedDateTime.isValid()
          ? serviceSupportRole.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceSupportRole: IServiceSupportRole) => {
        serviceSupportRole.createdDateTime = serviceSupportRole.createdDateTime != null ? moment(serviceSupportRole.createdDateTime) : null;
        serviceSupportRole.modifiedDateTime =
          serviceSupportRole.modifiedDateTime != null ? moment(serviceSupportRole.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
