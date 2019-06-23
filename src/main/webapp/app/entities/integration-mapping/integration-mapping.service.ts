import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIntegrationMapping } from 'app/shared/model/integration-mapping.model';

type EntityResponseType = HttpResponse<IIntegrationMapping>;
type EntityArrayResponseType = HttpResponse<IIntegrationMapping[]>;

@Injectable({ providedIn: 'root' })
export class IntegrationMappingService {
  public resourceUrl = SERVER_API_URL + 'api/integration-mappings';

  constructor(protected http: HttpClient) {}

  create(integrationMapping: IIntegrationMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrationMapping);
    return this.http
      .post<IIntegrationMapping>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(integrationMapping: IIntegrationMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(integrationMapping);
    return this.http
      .put<IIntegrationMapping>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIntegrationMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIntegrationMapping[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(integrationMapping: IIntegrationMapping): IIntegrationMapping {
    const copy: IIntegrationMapping = Object.assign({}, integrationMapping, {
      createdDateTime:
        integrationMapping.createdDateTime != null && integrationMapping.createdDateTime.isValid()
          ? integrationMapping.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        integrationMapping.modifiedDateTime != null && integrationMapping.modifiedDateTime.isValid()
          ? integrationMapping.modifiedDateTime.toJSON()
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
      res.body.forEach((integrationMapping: IIntegrationMapping) => {
        integrationMapping.createdDateTime = integrationMapping.createdDateTime != null ? moment(integrationMapping.createdDateTime) : null;
        integrationMapping.modifiedDateTime =
          integrationMapping.modifiedDateTime != null ? moment(integrationMapping.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
