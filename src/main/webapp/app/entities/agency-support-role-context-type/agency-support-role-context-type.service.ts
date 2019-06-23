import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';

type EntityResponseType = HttpResponse<IAgencySupportRoleContextType>;
type EntityArrayResponseType = HttpResponse<IAgencySupportRoleContextType[]>;

@Injectable({ providedIn: 'root' })
export class AgencySupportRoleContextTypeService {
  public resourceUrl = SERVER_API_URL + 'api/agency-support-role-context-types';

  constructor(protected http: HttpClient) {}

  create(agencySupportRoleContextType: IAgencySupportRoleContextType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agencySupportRoleContextType);
    return this.http
      .post<IAgencySupportRoleContextType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agencySupportRoleContextType: IAgencySupportRoleContextType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agencySupportRoleContextType);
    return this.http
      .put<IAgencySupportRoleContextType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgencySupportRoleContextType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgencySupportRoleContextType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(agencySupportRoleContextType: IAgencySupportRoleContextType): IAgencySupportRoleContextType {
    const copy: IAgencySupportRoleContextType = Object.assign({}, agencySupportRoleContextType, {
      createdDateTime:
        agencySupportRoleContextType.createdDateTime != null && agencySupportRoleContextType.createdDateTime.isValid()
          ? agencySupportRoleContextType.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        agencySupportRoleContextType.modifiedDateTime != null && agencySupportRoleContextType.modifiedDateTime.isValid()
          ? agencySupportRoleContextType.modifiedDateTime.toJSON()
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
      res.body.forEach((agencySupportRoleContextType: IAgencySupportRoleContextType) => {
        agencySupportRoleContextType.createdDateTime =
          agencySupportRoleContextType.createdDateTime != null ? moment(agencySupportRoleContextType.createdDateTime) : null;
        agencySupportRoleContextType.modifiedDateTime =
          agencySupportRoleContextType.modifiedDateTime != null ? moment(agencySupportRoleContextType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
