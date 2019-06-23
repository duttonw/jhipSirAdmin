import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICategoryType } from 'app/shared/model/category-type.model';

type EntityResponseType = HttpResponse<ICategoryType>;
type EntityArrayResponseType = HttpResponse<ICategoryType[]>;

@Injectable({ providedIn: 'root' })
export class CategoryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/category-types';

  constructor(protected http: HttpClient) {}

  create(categoryType: ICategoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoryType);
    return this.http
      .post<ICategoryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(categoryType: ICategoryType): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(categoryType);
    return this.http
      .put<ICategoryType>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICategoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICategoryType[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(categoryType: ICategoryType): ICategoryType {
    const copy: ICategoryType = Object.assign({}, categoryType, {
      createdDateTime:
        categoryType.createdDateTime != null && categoryType.createdDateTime.isValid() ? categoryType.createdDateTime.toJSON() : null,
      modifiedDateTime:
        categoryType.modifiedDateTime != null && categoryType.modifiedDateTime.isValid() ? categoryType.modifiedDateTime.toJSON() : null
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
      res.body.forEach((categoryType: ICategoryType) => {
        categoryType.createdDateTime = categoryType.createdDateTime != null ? moment(categoryType.createdDateTime) : null;
        categoryType.modifiedDateTime = categoryType.modifiedDateTime != null ? moment(categoryType.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
