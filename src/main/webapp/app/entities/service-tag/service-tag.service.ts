import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceTag } from 'app/shared/model/service-tag.model';

type EntityResponseType = HttpResponse<IServiceTag>;
type EntityArrayResponseType = HttpResponse<IServiceTag[]>;

@Injectable({ providedIn: 'root' })
export class ServiceTagService {
  public resourceUrl = SERVER_API_URL + 'api/service-tags';

  constructor(protected http: HttpClient) {}

  create(serviceTag: IServiceTag): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceTag);
    return this.http
      .post<IServiceTag>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceTag: IServiceTag): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceTag);
    return this.http
      .put<IServiceTag>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceTag>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceTag[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceTag: IServiceTag): IServiceTag {
    const copy: IServiceTag = Object.assign({}, serviceTag, {
      createdDateTime:
        serviceTag.createdDateTime != null && serviceTag.createdDateTime.isValid() ? serviceTag.createdDateTime.toJSON() : null,
      modifiedDateTime:
        serviceTag.modifiedDateTime != null && serviceTag.modifiedDateTime.isValid() ? serviceTag.modifiedDateTime.toJSON() : null
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
      res.body.forEach((serviceTag: IServiceTag) => {
        serviceTag.createdDateTime = serviceTag.createdDateTime != null ? moment(serviceTag.createdDateTime) : null;
        serviceTag.modifiedDateTime = serviceTag.modifiedDateTime != null ? moment(serviceTag.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
