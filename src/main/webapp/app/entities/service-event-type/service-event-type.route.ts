import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceEventType } from 'app/shared/model/service-event-type.model';
import { ServiceEventTypeService } from './service-event-type.service';
import { ServiceEventTypeComponent } from './service-event-type.component';
import { ServiceEventTypeDetailComponent } from './service-event-type-detail.component';
import { ServiceEventTypeUpdateComponent } from './service-event-type-update.component';
import { ServiceEventTypeDeletePopupComponent } from './service-event-type-delete-dialog.component';
import { IServiceEventType } from 'app/shared/model/service-event-type.model';

@Injectable({ providedIn: 'root' })
export class ServiceEventTypeResolve implements Resolve<IServiceEventType> {
  constructor(private service: ServiceEventTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceEventType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceEventType>) => response.ok),
        map((serviceEventType: HttpResponse<ServiceEventType>) => serviceEventType.body)
      );
    }
    return of(new ServiceEventType());
  }
}

export const serviceEventTypeRoute: Routes = [
  {
    path: '',
    component: ServiceEventTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceEventType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceEventTypeDetailComponent,
    resolve: {
      serviceEventType: ServiceEventTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEventType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceEventTypeUpdateComponent,
    resolve: {
      serviceEventType: ServiceEventTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEventType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceEventTypeUpdateComponent,
    resolve: {
      serviceEventType: ServiceEventTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEventType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceEventTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceEventTypeDeletePopupComponent,
    resolve: {
      serviceEventType: ServiceEventTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEventType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
