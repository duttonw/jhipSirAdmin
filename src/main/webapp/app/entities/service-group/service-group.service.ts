import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceGroup } from 'app/shared/model/service-group.model';

type EntityResponseType = HttpResponse<IServiceGroup>;
type EntityArrayResponseType = HttpResponse<IServiceGroup[]>;

@Injectable({ providedIn: 'root' })
export class ServiceGroupService {
  public resourceUrl = SERVER_API_URL + 'api/service-groups';

  constructor(protected http: HttpClient) {}

  create(serviceGroup: IServiceGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceGroup);
    return this.http
      .post<IServiceGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceGroup: IServiceGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceGroup);
    return this.http
      .put<IServiceGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceGroup: IServiceGroup): IServiceGroup {
    const copy: IServiceGroup = Object.assign({}, serviceGroup, {
      createdDateTime:
        serviceGroup.createdDateTime != null && serviceGroup.createdDateTime.isValid() ? serviceGroup.createdDateTime.toJSON() : null,
      modifiedDateTime:
        serviceGroup.modifiedDateTime != null && serviceGroup.modifiedDateTime.isValid() ? serviceGroup.modifiedDateTime.toJSON() : null
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
      res.body.forEach((serviceGroup: IServiceGroup) => {
        serviceGroup.createdDateTime = serviceGroup.createdDateTime != null ? moment(serviceGroup.createdDateTime) : null;
        serviceGroup.modifiedDateTime = serviceGroup.modifiedDateTime != null ? moment(serviceGroup.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
