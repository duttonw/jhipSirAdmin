import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceEvent } from 'app/shared/model/service-event.model';

type EntityResponseType = HttpResponse<IServiceEvent>;
type EntityArrayResponseType = HttpResponse<IServiceEvent[]>;

@Injectable({ providedIn: 'root' })
export class ServiceEventService {
  public resourceUrl = SERVER_API_URL + 'api/service-events';

  constructor(protected http: HttpClient) {}

  create(serviceEvent: IServiceEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceEvent);
    return this.http
      .post<IServiceEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceEvent: IServiceEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceEvent);
    return this.http
      .put<IServiceEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceEvent: IServiceEvent): IServiceEvent {
    const copy: IServiceEvent = Object.assign({}, serviceEvent, {
      createdDateTime:
        serviceEvent.createdDateTime != null && serviceEvent.createdDateTime.isValid() ? serviceEvent.createdDateTime.toJSON() : null,
      modifiedDateTime:
        serviceEvent.modifiedDateTime != null && serviceEvent.modifiedDateTime.isValid() ? serviceEvent.modifiedDateTime.toJSON() : null
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
      res.body.forEach((serviceEvent: IServiceEvent) => {
        serviceEvent.createdDateTime = serviceEvent.createdDateTime != null ? moment(serviceEvent.createdDateTime) : null;
        serviceEvent.modifiedDateTime = serviceEvent.modifiedDateTime != null ? moment(serviceEvent.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
