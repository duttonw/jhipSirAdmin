import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from './service-record.service';
import { ServiceRecordComponent } from './service-record.component';
import { ServiceRecordDetailComponent } from './service-record-detail.component';
import { ServiceRecordUpdateComponent } from './service-record-update.component';
import { ServiceRecordDeletePopupComponent } from './service-record-delete-dialog.component';
import { IServiceRecord } from 'app/shared/model/service-record.model';

@Injectable({ providedIn: 'root' })
export class ServiceRecordResolve implements Resolve<IServiceRecord> {
  constructor(private service: ServiceRecordService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceRecord> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceRecord>) => response.ok),
        map((serviceRecord: HttpResponse<ServiceRecord>) => serviceRecord.body)
      );
    }
    return of(new ServiceRecord());
  }
}

export const serviceRecordRoute: Routes = [
  {
    path: '',
    component: ServiceRecordComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceRecordDetailComponent,
    resolve: {
      serviceRecord: ServiceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceRecordUpdateComponent,
    resolve: {
      serviceRecord: ServiceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceRecordUpdateComponent,
    resolve: {
      serviceRecord: ServiceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRecord.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceRecordPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceRecordDeletePopupComponent,
    resolve: {
      serviceRecord: ServiceRecordResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRecord.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
