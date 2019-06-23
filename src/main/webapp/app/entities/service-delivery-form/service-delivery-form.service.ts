import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';

type EntityResponseType = HttpResponse<IServiceDeliveryForm>;
type EntityArrayResponseType = HttpResponse<IServiceDeliveryForm[]>;

@Injectable({ providedIn: 'root' })
export class ServiceDeliveryFormService {
  public resourceUrl = SERVER_API_URL + 'api/service-delivery-forms';

  constructor(protected http: HttpClient) {}

  create(serviceDeliveryForm: IServiceDeliveryForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceDeliveryForm);
    return this.http
      .post<IServiceDeliveryForm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceDeliveryForm: IServiceDeliveryForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceDeliveryForm);
    return this.http
      .put<IServiceDeliveryForm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceDeliveryForm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceDeliveryForm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceDeliveryForm: IServiceDeliveryForm): IServiceDeliveryForm {
    const copy: IServiceDeliveryForm = Object.assign({}, serviceDeliveryForm, {
      createdDateTime:
        serviceDeliveryForm.createdDateTime != null && serviceDeliveryForm.createdDateTime.isValid()
          ? serviceDeliveryForm.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceDeliveryForm.modifiedDateTime != null && serviceDeliveryForm.modifiedDateTime.isValid()
          ? serviceDeliveryForm.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceDeliveryForm: IServiceDeliveryForm) => {
        serviceDeliveryForm.createdDateTime =
          serviceDeliveryForm.createdDateTime != null ? moment(serviceDeliveryForm.createdDateTime) : null;
        serviceDeliveryForm.modifiedDateTime =
          serviceDeliveryForm.modifiedDateTime != null ? moment(serviceDeliveryForm.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
