import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgencyType } from 'app/shared/model/agency-type.model';

type EntityResponseType = HttpResponse<IAgencyType>;
type EntityArrayResponseType = HttpResponse<IAgencyType[]>;

@Injectable({ providedIn: 'root' })
export class AgencyTypeService {
  public resourceUrl = SERVER_API_URL + 'api/agency-types';

  constructor(protected http: HttpClient) {}

  create(agencyType: IAgencyType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agencyType);
    return this.http
      .post<IAgencyType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agencyType: IAgencyType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agencyType);
    return this.http
      .put<IAgencyType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgencyType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgencyType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(agencyType: IAgencyType): IAgencyType {
    const copy: IAgencyType = Object.assign({}, agencyType, {
      createdDateTime:
        agencyType.createdDateTime != null && agencyType.createdDateTime.isValid() ? agencyType.createdDateTime.toJSON() : null,
      modifiedDateTime:
        agencyType.modifiedDateTime != null && agencyType.modifiedDateTime.isValid() ? agencyType.modifiedDateTime.toJSON() : null
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
      res.body.forEach((agencyType: IAgencyType) => {
        agencyType.createdDateTime = agencyType.createdDateTime != null ? moment(agencyType.createdDateTime) : null;
        agencyType.modifiedDateTime = agencyType.modifiedDateTime != null ? moment(agencyType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
