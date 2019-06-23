import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceRelationship } from 'app/shared/model/service-relationship.model';

type EntityResponseType = HttpResponse<IServiceRelationship>;
type EntityArrayResponseType = HttpResponse<IServiceRelationship[]>;

@Injectable({ providedIn: 'root' })
export class ServiceRelationshipService {
  public resourceUrl = SERVER_API_URL + 'api/service-relationships';

  constructor(protected http: HttpClient) {}

  create(serviceRelationship: IServiceRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRelationship);
    return this.http
      .post<IServiceRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceRelationship: IServiceRelationship): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceRelationship);
    return this.http
      .put<IServiceRelationship>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceRelationship>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceRelationship[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceRelationship: IServiceRelationship): IServiceRelationship {
    const copy: IServiceRelationship = Object.assign({}, serviceRelationship, {
      createdDateTime:
        serviceRelationship.createdDateTime != null && serviceRelationship.createdDateTime.isValid()
          ? serviceRelationship.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceRelationship.modifiedDateTime != null && serviceRelationship.modifiedDateTime.isValid()
          ? serviceRelationship.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceRelationship: IServiceRelationship) => {
        serviceRelationship.createdDateTime =
          serviceRelationship.createdDateTime != null ? moment(serviceRelationship.createdDateTime) : null;
        serviceRelationship.modifiedDateTime =
          serviceRelationship.modifiedDateTime != null ? moment(serviceRelationship.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
