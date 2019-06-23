import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAgency } from 'app/shared/model/agency.model';

type EntityResponseType = HttpResponse<IAgency>;
type EntityArrayResponseType = HttpResponse<IAgency[]>;

@Injectable({ providedIn: 'root' })
export class AgencyService {
  public resourceUrl = SERVER_API_URL + 'api/agencies';

  constructor(protected http: HttpClient) {}

  create(agency: IAgency): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agency);
    return this.http
      .post<IAgency>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(agency: IAgency): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agency);
    return this.http
      .put<IAgency>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAgency>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAgency[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(agency: IAgency): IAgency {
    const copy: IAgency = Object.assign({}, agency, {
      createdDateTime: agency.createdDateTime != null && agency.createdDateTime.isValid() ? agency.createdDateTime.toJSON() : null,
      modifiedDateTime: agency.modifiedDateTime != null && agency.modifiedDateTime.isValid() ? agency.modifiedDateTime.toJSON() : null
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
      res.body.forEach((agency: IAgency) => {
        agency.createdDateTime = agency.createdDateTime != null ? moment(agency.createdDateTime) : null;
        agency.modifiedDateTime = agency.modifiedDateTime != null ? moment(agency.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
