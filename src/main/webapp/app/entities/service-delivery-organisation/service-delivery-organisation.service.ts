import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';

type EntityResponseType = HttpResponse<IServiceDeliveryOrganisation>;
type EntityArrayResponseType = HttpResponse<IServiceDeliveryOrganisation[]>;

@Injectable({ providedIn: 'root' })
export class ServiceDeliveryOrganisationService {
  public resourceUrl = SERVER_API_URL + 'api/service-delivery-organisations';

  constructor(protected http: HttpClient) {}

  create(serviceDeliveryOrganisation: IServiceDeliveryOrganisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceDeliveryOrganisation);
    return this.http
      .post<IServiceDeliveryOrganisation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceDeliveryOrganisation: IServiceDeliveryOrganisation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceDeliveryOrganisation);
    return this.http
      .put<IServiceDeliveryOrganisation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceDeliveryOrganisation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceDeliveryOrganisation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceDeliveryOrganisation: IServiceDeliveryOrganisation): IServiceDeliveryOrganisation {
    const copy: IServiceDeliveryOrganisation = Object.assign({}, serviceDeliveryOrganisation, {
      createdDateTime:
        serviceDeliveryOrganisation.createdDateTime != null && serviceDeliveryOrganisation.createdDateTime.isValid()
          ? serviceDeliveryOrganisation.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceDeliveryOrganisation.modifiedDateTime != null && serviceDeliveryOrganisation.modifiedDateTime.isValid()
          ? serviceDeliveryOrganisation.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceDeliveryOrganisation: IServiceDeliveryOrganisation) => {
        serviceDeliveryOrganisation.createdDateTime =
          serviceDeliveryOrganisation.createdDateTime != null ? moment(serviceDeliveryOrganisation.createdDateTime) : null;
        serviceDeliveryOrganisation.modifiedDateTime =
          serviceDeliveryOrganisation.modifiedDateTime != null ? moment(serviceDeliveryOrganisation.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
