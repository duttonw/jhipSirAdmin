import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceDescription } from 'app/shared/model/service-description.model';

type EntityResponseType = HttpResponse<IServiceDescription>;
type EntityArrayResponseType = HttpResponse<IServiceDescription[]>;

@Injectable({ providedIn: 'root' })
export class ServiceDescriptionService {
  public resourceUrl = SERVER_API_URL + 'api/service-descriptions';

  constructor(protected http: HttpClient) {}

  create(serviceDescription: IServiceDescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceDescription);
    return this.http
      .post<IServiceDescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceDescription: IServiceDescription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceDescription);
    return this.http
      .put<IServiceDescription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceDescription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceDescription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceDescription: IServiceDescription): IServiceDescription {
    const copy: IServiceDescription = Object.assign({}, serviceDescription, {
      createdDateTime:
        serviceDescription.createdDateTime != null && serviceDescription.createdDateTime.isValid()
          ? serviceDescription.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceDescription.modifiedDateTime != null && serviceDescription.modifiedDateTime.isValid()
          ? serviceDescription.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceDescription: IServiceDescription) => {
        serviceDescription.createdDateTime = serviceDescription.createdDateTime != null ? moment(serviceDescription.createdDateTime) : null;
        serviceDescription.modifiedDateTime =
          serviceDescription.modifiedDateTime != null ? moment(serviceDescription.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
