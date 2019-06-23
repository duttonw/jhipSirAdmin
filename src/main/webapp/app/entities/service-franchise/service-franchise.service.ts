import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceFranchise } from 'app/shared/model/service-franchise.model';

type EntityResponseType = HttpResponse<IServiceFranchise>;
type EntityArrayResponseType = HttpResponse<IServiceFranchise[]>;

@Injectable({ providedIn: 'root' })
export class ServiceFranchiseService {
  public resourceUrl = SERVER_API_URL + 'api/service-franchises';

  constructor(protected http: HttpClient) {}

  create(serviceFranchise: IServiceFranchise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceFranchise);
    return this.http
      .post<IServiceFranchise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(serviceFranchise: IServiceFranchise): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(serviceFranchise);
    return this.http
      .put<IServiceFranchise>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IServiceFranchise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IServiceFranchise[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(serviceFranchise: IServiceFranchise): IServiceFranchise {
    const copy: IServiceFranchise = Object.assign({}, serviceFranchise, {
      createdDateTime:
        serviceFranchise.createdDateTime != null && serviceFranchise.createdDateTime.isValid()
          ? serviceFranchise.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        serviceFranchise.modifiedDateTime != null && serviceFranchise.modifiedDateTime.isValid()
          ? serviceFranchise.modifiedDateTime.toJSON()
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
      res.body.forEach((serviceFranchise: IServiceFranchise) => {
        serviceFranchise.createdDateTime = serviceFranchise.createdDateTime != null ? moment(serviceFranchise.createdDateTime) : null;
        serviceFranchise.modifiedDateTime = serviceFranchise.modifiedDateTime != null ? moment(serviceFranchise.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
