import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceEvidence } from 'app/shared/model/service-evidence.model';

type EntityResponseType = HttpResponse<IServiceEvidence>;
type EntityArrayResponseType = HttpResponse<IServiceEvidence[]>;

@Injectable({ providedIn: 'root' })
export class ServiceEvidenceService {
  public resourceUrl = SERVER_API_URL + 'api/service-evidences';

  constructor(protected http: HttpClient) {}

  create(serviceEvidence: IServiceEvidence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceEvidence);
    return this.http
      .post<IServiceEvidence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceEvidence: IServiceEvidence): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceEvidence);
    return this.http
      .put<IServiceEvidence>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceEvidence>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceEvidence[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceEvidence: IServiceEvidence): IServiceEvidence {
    const copy: IServiceEvidence = Object.assign({}, serviceEvidence, {
      createdDateTime:
        serviceEvidence.createdDateTime != null && serviceEvidence.createdDateTime.isValid()
          ? serviceEvidence.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceEvidence.modifiedDateTime != null && serviceEvidence.modifiedDateTime.isValid()
          ? serviceEvidence.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceEvidence: IServiceEvidence) => {
        serviceEvidence.createdDateTime = serviceEvidence.createdDateTime != null ? moment(serviceEvidence.createdDateTime) : null;
        serviceEvidence.modifiedDateTime = serviceEvidence.modifiedDateTime != null ? moment(serviceEvidence.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
