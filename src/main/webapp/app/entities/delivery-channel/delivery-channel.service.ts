import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDeliveryChannel } from 'app/shared/model/delivery-channel.model';

type EntityResponseType = HttpResponse<IDeliveryChannel>;
type EntityArrayResponseType = HttpResponse<IDeliveryChannel[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryChannelService {
  public resourceUrl = SERVER_API_URL + 'api/delivery-channels';

  constructor(protected http: HttpClient) {}

  create(deliveryChannel: IDeliveryChannel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryChannel);
    return this.http
      .post<IDeliveryChannel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(deliveryChannel: IDeliveryChannel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(deliveryChannel);
    return this.http
      .put<IDeliveryChannel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDeliveryChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDeliveryChannel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(deliveryChannel: IDeliveryChannel): IDeliveryChannel {
    const copy: IDeliveryChannel = Object.assign({}, deliveryChannel, {
      createdDateTime:
        deliveryChannel.createdDateTime != null && deliveryChannel.createdDateTime.isValid()
          ? deliveryChannel.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        deliveryChannel.modifiedDateTime != null && deliveryChannel.modifiedDateTime.isValid()
          ? deliveryChannel.modifiedDateTime.toJSON()
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
      res.body.forEach((deliveryChannel: IDeliveryChannel) => {
        deliveryChannel.createdDateTime = deliveryChannel.createdDateTime != null ? moment(deliveryChannel.createdDateTime) : null;
        deliveryChannel.modifiedDateTime = deliveryChannel.modifiedDateTime != null ? moment(deliveryChannel.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
