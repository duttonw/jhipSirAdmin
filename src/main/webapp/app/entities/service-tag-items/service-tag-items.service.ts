import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceTagItems } from 'app/shared/model/service-tag-items.model';

type EntityResponseType = HttpResponse<IServiceTagItems>;
type EntityArrayResponseType = HttpResponse<IServiceTagItems[]>;

@Injectable({ providedIn: 'root' })
export class ServiceTagItemsService {
  public resourceUrl = SERVER_API_URL + 'api/service-tag-items';

  constructor(protected http: HttpClient) {}

  create(serviceTagItems: IServiceTagItems): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceTagItems);
    return this.http
      .post<IServiceTagItems>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceTagItems: IServiceTagItems): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceTagItems);
    return this.http
      .put<IServiceTagItems>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceTagItems>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceTagItems[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceTagItems: IServiceTagItems): IServiceTagItems {
    const copy: IServiceTagItems = Object.assign({}, serviceTagItems, {
      createdDateTime:
        serviceTagItems.createdDateTime != null && serviceTagItems.createdDateTime.isValid()
          ? serviceTagItems.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceTagItems.modifiedDateTime != null && serviceTagItems.modifiedDateTime.isValid()
          ? serviceTagItems.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceTagItems: IServiceTagItems) => {
        serviceTagItems.createdDateTime = serviceTagItems.createdDateTime != null ? moment(serviceTagItems.createdDateTime) : null;
        serviceTagItems.modifiedDateTime = serviceTagItems.modifiedDateTime != null ? moment(serviceTagItems.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
