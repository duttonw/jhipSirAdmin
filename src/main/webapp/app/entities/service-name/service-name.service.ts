import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceName } from 'app/shared/model/service-name.model';

type EntityResponseType = HttpResponse<IServiceName>;
type EntityArrayResponseType = HttpResponse<IServiceName[]>;

@Injectable({ providedIn: 'root' })
export class ServiceNameService {
  public resourceUrl = SERVER_API_URL + 'api/service-names';

  constructor(protected http: HttpClient) {}

  create(serviceName: IServiceName): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceName);
    return this.http
      .post<IServiceName>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceName: IServiceName): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceName);
    return this.http
      .put<IServiceName>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceName>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceName[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceName: IServiceName): IServiceName {
    const copy: IServiceName = Object.assign({}, serviceName, {
      createdDateTime:
        serviceName.createdDateTime != null && serviceName.createdDateTime.isValid() ? serviceName.createdDateTime.toJSON() : null,
      modifiedDateTime:
        serviceName.modifiedDateTime != null && serviceName.modifiedDateTime.isValid() ? serviceName.modifiedDateTime.toJSON() : null
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
      res.body.forEach((serviceName: IServiceName) => {
        serviceName.createdDateTime = serviceName.createdDateTime != null ? moment(serviceName.createdDateTime) : null;
        serviceName.modifiedDateTime = serviceName.modifiedDateTime != null ? moment(serviceName.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
