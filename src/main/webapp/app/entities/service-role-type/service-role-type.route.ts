import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceRoleType } from 'app/shared/model/service-role-type.model';
import { ServiceRoleTypeService } from './service-role-type.service';
import { ServiceRoleTypeComponent } from './service-role-type.component';
import { ServiceRoleTypeDetailComponent } from './service-role-type-detail.component';
import { ServiceRoleTypeUpdateComponent } from './service-role-type-update.component';
import { ServiceRoleTypeDeletePopupComponent } from './service-role-type-delete-dialog.component';
import { IServiceRoleType } from 'app/shared/model/service-role-type.model';

@Injectable({ providedIn: 'root' })
export class ServiceRoleTypeResolve implements Resolve<IServiceRoleType> {
  constructor(private service: ServiceRoleTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceRoleType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceRoleType>) => response.ok),
        map((serviceRoleType: HttpResponse<ServiceRoleType>) => serviceRoleType.body)
      );
    }
    return of(new ServiceRoleType());
  }
}

export const serviceRoleTypeRoute: Routes = [
  {
    path: '',
    component: ServiceRoleTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceRoleType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceRoleTypeDetailComponent,
    resolve: {
      serviceRoleType: ServiceRoleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRoleType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceRoleTypeUpdateComponent,
    resolve: {
      serviceRoleType: ServiceRoleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRoleType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceRoleTypeUpdateComponent,
    resolve: {
      serviceRoleType: ServiceRoleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRoleType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceRoleTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceRoleTypeDeletePopupComponent,
    resolve: {
      serviceRoleType: ServiceRoleTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRoleType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
