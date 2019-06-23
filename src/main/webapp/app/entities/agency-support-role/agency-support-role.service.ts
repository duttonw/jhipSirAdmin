import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';

type EntityResponseType = HttpResponse<IAgencySupportRole>;
type EntityArrayResponseType = HttpResponse<IAgencySupportRole[]>;

@Injectable({ providedIn: 'root' })
export class AgencySupportRoleService {
  public resourceUrl = SERVER_API_URL + 'api/agency-support-roles';

  constructor(protected http: HttpClient) {}

  create(agencySupportRole: IAgencySupportRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agencySupportRole);
    return this.http
      .post<IAgencySupportRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agencySupportRole: IAgencySupportRole): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agencySupportRole);
    return this.http
      .put<IAgencySupportRole>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgencySupportRole>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgencySupportRole[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(agencySupportRole: IAgencySupportRole): IAgencySupportRole {
    const copy: IAgencySupportRole = Object.assign({}, agencySupportRole, {
      createdDateTime:
        agencySupportRole.createdDateTime != null && agencySupportRole.createdDateTime.isValid()
          ? agencySupportRole.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        agencySupportRole.modifiedDateTime != null && agencySupportRole.modifiedDateTime.isValid()
          ? agencySupportRole.modifiedDateTime.toJSON()
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
      res.body.forEach((agencySupportRole: IAgencySupportRole) => {
        agencySupportRole.createdDateTime = agencySupportRole.createdDateTime != null ? moment(agencySupportRole.createdDateTime) : null;
        agencySupportRole.modifiedDateTime = agencySupportRole.modifiedDateTime != null ? moment(agencySupportRole.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
