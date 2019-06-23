import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILocationAddress } from 'app/shared/model/location-address.model';

type EntityResponseType = HttpResponse<ILocationAddress>;
type EntityArrayResponseType = HttpResponse<ILocationAddress[]>;

@Injectable({ providedIn: 'root' })
export class LocationAddressService {
  public resourceUrl = SERVER_API_URL + 'api/location-addresses';

  constructor(protected http: HttpClient) {}

  create(locationAddress: ILocationAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationAddress);
    return this.http
      .post<ILocationAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(locationAddress: ILocationAddress): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(locationAddress);
    return this.http
      .put<ILocationAddress>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ILocationAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ILocationAddress[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(locationAddress: ILocationAddress): ILocationAddress {
    const copy: ILocationAddress = Object.assign({}, locationAddress, {
      createdDateTime:
        locationAddress.createdDateTime != null && locationAddress.createdDateTime.isValid()
          ? locationAddress.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        locationAddress.modifiedDateTime != null && locationAddress.modifiedDateTime.isValid()
          ? locationAddress.modifiedDateTime.toJSON()
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
      res.body.forEach((locationAddress: ILocationAddress) => {
        locationAddress.createdDateTime = locationAddress.createdDateTime != null ? moment(locationAddress.createdDateTime) : null;
        locationAddress.modifiedDateTime = locationAddress.modifiedDateTime != null ? moment(locationAddress.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
