import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';

type EntityResponseType = HttpResponse<IOpeningHoursSpecification>;
type EntityArrayResponseType = HttpResponse<IOpeningHoursSpecification[]>;

@Injectable({ providedIn: 'root' })
export class OpeningHoursSpecificationService {
  public resourceUrl = SERVER_API_URL + 'api/opening-hours-specifications';

  constructor(protected http: HttpClient) {}

  create(openingHoursSpecification: IOpeningHoursSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(openingHoursSpecification);
    return this.http
      .post<IOpeningHoursSpecification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(openingHoursSpecification: IOpeningHoursSpecification): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(openingHoursSpecification);
    return this.http
      .put<IOpeningHoursSpecification>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOpeningHoursSpecification>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOpeningHoursSpecification[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(openingHoursSpecification: IOpeningHoursSpecification): IOpeningHoursSpecification {
    const copy: IOpeningHoursSpecification = Object.assign({}, openingHoursSpecification, {
      createdDateTime:
        openingHoursSpecification.createdDateTime != null && openingHoursSpecification.createdDateTime.isValid()
          ? openingHoursSpecification.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        openingHoursSpecification.modifiedDateTime != null && openingHoursSpecification.modifiedDateTime.isValid()
          ? openingHoursSpecification.modifiedDateTime.toJSON()
          : null,
      validFrom:
        openingHoursSpecification.validFrom != null && openingHoursSpecification.validFrom.isValid()
          ? openingHoursSpecification.validFrom.toJSON()
          : null,
      validTo:
        openingHoursSpecification.validTo != null && openingHoursSpecification.validTo.isValid()
          ? openingHoursSpecification.validTo.toJSON()
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDateTime = res.body.createdDateTime != null ? moment(res.body.createdDateTime) : null;
      res.body.modifiedDateTime = res.body.modifiedDateTime != null ? moment(res.body.modifiedDateTime) : null;
      res.body.validFrom = res.body.validFrom != null ? moment(res.body.validFrom) : null;
      res.body.validTo = res.body.validTo != null ? moment(res.body.validTo) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((openingHoursSpecification: IOpeningHoursSpecification) => {
        openingHoursSpecification.createdDateTime =
          openingHoursSpecification.createdDateTime != null ? moment(openingHoursSpecification.createdDateTime) : null;
        openingHoursSpecification.modifiedDateTime =
          openingHoursSpecification.modifiedDateTime != null ? moment(openingHoursSpecification.modifiedDateTime) : null;
        openingHoursSpecification.validFrom =
          openingHoursSpecification.validFrom != null ? moment(openingHoursSpecification.validFrom) : null;
        openingHoursSpecification.validTo = openingHoursSpecification.validTo != null ? moment(openingHoursSpecification.validTo) : null;
      });
    }
    return res;
  }
}
