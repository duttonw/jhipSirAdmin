import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';

type EntityResponseType = HttpResponse<IApplicationServiceOverrideTag>;
type EntityArrayResponseType = HttpResponse<IApplicationServiceOverrideTag[]>;

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideTagService {
  public resourceUrl = SERVER_API_URL + 'api/application-service-override-tags';

  constructor(protected http: HttpClient) {}

  create(applicationServiceOverrideTag: IApplicationServiceOverrideTag): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverrideTag);
    return this.http
      .post<IApplicationServiceOverrideTag>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(applicationServiceOverrideTag: IApplicationServiceOverrideTag): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(applicationServiceOverrideTag);
    return this.http
      .put<IApplicationServiceOverrideTag>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IApplicationServiceOverrideTag>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IApplicationServiceOverrideTag[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(applicationServiceOverrideTag: IApplicationServiceOverrideTag): IApplicationServiceOverrideTag {
    const copy: IApplicationServiceOverrideTag = Object.assign({}, applicationServiceOverrideTag, {
      createdDateTime:
        applicationServiceOverrideTag.createdDateTime != null && applicationServiceOverrideTag.createdDateTime.isValid()
          ? applicationServiceOverrideTag.createdDateTime.toJSON()
          : null,
      modifiedDateTime:
        applicationServiceOverrideTag.modifiedDateTime != null && applicationServiceOverrideTag.modifiedDateTime.isValid()
          ? applicationServiceOverrideTag.modifiedDateTime.toJSON()
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
      res.body.forEach((applicationServiceOverrideTag: IApplicationServiceOverrideTag) => {
        applicationServiceOverrideTag.createdDateTime =
          applicationServiceOverrideTag.createdDateTime != null ? moment(applicationServiceOverrideTag.createdDateTime) : null;
        applicationServiceOverrideTag.modifiedDateTime =
          applicationServiceOverrideTag.modifiedDateTime != null ? moment(applicationServiceOverrideTag.modifiedDateTime) : null;
      });
    }
    return res;
  }
}
