import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

type EntityResponseType = HttpResponse<IApplicationServiceOverrideTagItems>;
type EntityArrayResponseType = HttpResponse<IApplicationServiceOverrideTagItems[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideTagItemsService {
  public resourceUrl = SERVER_API_URL + 'api/application-service-override-tag-items';

  constructor(protected http: HttpClient) {}

  create(applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverrideTagItems);
    return this.http
      .post<IApplicationServiceOverrideTagItems>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverrideTagItems);
    return this.http
      .put<IApplicationServiceOverrideTagItems>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationServiceOverrideTagItems>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationServiceOverrideTagItems[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(
    applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems
  ): IApplicationServiceOverrideTagItems {
    const copy: IApplicationServiceOverrideTagItems = Object.assign({}, applicationServiceOverrideTagItems, {
      createdDateTime:
        applicationServiceOverrideTagItems.createdDateTime != null && applicationServiceOverrideTagItems.createdDateTime.isValid()
          ? applicationServiceOverrideTagItems.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        applicationServiceOverrideTagItems.modifiedDateTime != null && applicationServiceOverrideTagItems.modifiedDateTime.isValid()
          ? applicationServiceOverrideTagItems.modifiedDateTime.toJSON()
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
      res.body.forEach((applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems) => {
        applicationServiceOverrideTagItems.createdDateTime =
          applicationServiceOverrideTagItems.createdDateTime != null ? moment(applicationServiceOverrideTagItems.createdDateTime) : null;
        applicationServiceOverrideTagItems.modifiedDateTime =
          applicationServiceOverrideTagItems.modifiedDateTime != null ? moment(applicationServiceOverrideTagItems.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
