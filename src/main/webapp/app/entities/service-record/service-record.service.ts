import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceRecord } from 'app/shared/model/service-record.model';

type EntityResponseType = HttpResponse<IServiceRecord>;
type EntityArrayResponseType = HttpResponse<IServiceRecord[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRecordService {
  public resourceUrl = SERVER_API_URL + 'api/service-records';

  constructor(protected http: HttpClient) {}

  create(serviceRecord: IServiceRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRecord);
    return this.http
      .post<IServiceRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceRecord: IServiceRecord): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRecord);
    return this.http
      .put<IServiceRecord>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceRecord>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceRecord[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceRecord: IServiceRecord): IServiceRecord {
    const copy: IServiceRecord = Object.assign({}, serviceRecord, {
      createdDateTime:
        serviceRecord.createdDateTime != null && serviceRecord.createdDateTime.isValid() ? serviceRecord.createdDateTime.toJSON() : null,
      modifiedDateTime:
        serviceRecord.modifiedDateTime != null && serviceRecord.modifiedDateTime.isValid() ? serviceRecord.modifiedDateTime.toJSON() : null,
      validatedDate:
        serviceRecord.validatedDate != null && serviceRecord.validatedDate.isValid() ? serviceRecord.validatedDate.toJSON() : null,
      startDate: serviceRecord.startDate != null && serviceRecord.startDate.isValid() ? serviceRecord.startDate.format(DATE_FORMAT) : null,
      endDate: serviceRecord.endDate != null && serviceRecord.endDate.isValid() ? serviceRecord.endDate.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDateTime = res.body.createdDateTime != null ? moment(res.body.createdDateTime) : null;
      res.body.modifiedDateTime = res.body.modifiedDateTime != null ? moment(res.body.modifiedDateTime) : null;
      res.body.validatedDate = res.body.validatedDate != null ? moment(res.body.validatedDate) : null;
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((serviceRecord: IServiceRecord) => {
        serviceRecord.createdDateTime = serviceRecord.createdDateTime != null ? moment(serviceRecord.createdDateTime) : null;
        serviceRecord.modifiedDateTime = serviceRecord.modifiedDateTime != null ? moment(serviceRecord.modifiedDateTime) : null;
        serviceRecord.validatedDate = serviceRecord.validatedDate != null ? moment(serviceRecord.validatedDate) : null;
        serviceRecord.startDate = serviceRecord.startDate != null ? moment(serviceRecord.startDate) : null;
        serviceRecord.endDate = serviceRecord.endDate != null ? moment(serviceRecord.endDate) : null;
      });
    }
    return res;
  }
}
