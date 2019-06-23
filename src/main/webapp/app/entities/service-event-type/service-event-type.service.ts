import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceEventType } from 'app/shared/model/service-event-type.model';

type EntityResponseType = HttpResponse<IServiceEventType>;
type EntityArrayResponseType = HttpResponse<IServiceEventType[]>;

@Injectable({ providedIn: 'root' })
export class ServiceEventTypeService {
  public resourceUrl = SERVER_API_URL + 'api/service-event-types';

  constructor(protected http: HttpClient) {}

  create(serviceEventType: IServiceEventType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceEventType);
    return this.http
      .post<IServiceEventType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceEventType: IServiceEventType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceEventType);
    return this.http
      .put<IServiceEventType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceEventType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceEventType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceEventType: IServiceEventType): IServiceEventType {
    const copy: IServiceEventType = Object.assign({}, serviceEventType, {
      createdDateTime:
        serviceEventType.createdDateTime != null && serviceEventType.createdDateTime.isValid()
          ? serviceEventType.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceEventType.modifiedDateTime != null && serviceEventType.modifiedDateTime.isValid()
          ? serviceEventType.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceEventType: IServiceEventType) => {
        serviceEventType.createdDateTime = serviceEventType.createdDateTime != null ? moment(serviceEventType.createdDateTime) : null;
        serviceEventType.modifiedDateTime = serviceEventType.modifiedDateTime != null ? moment(serviceEventType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
