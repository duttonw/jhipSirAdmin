import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';
import { ServiceSupportRoleContextTypeService } from './service-support-role-context-type.service';
import { ServiceSupportRoleContextTypeComponent } from './service-support-role-context-type.component';
import { ServiceSupportRoleContextTypeDetailComponent } from './service-support-role-context-type-detail.component';
import { ServiceSupportRoleContextTypeUpdateComponent } from './service-support-role-context-type-update.component';
import { ServiceSupportRoleContextTypeDeletePopupComponent } from './service-support-role-context-type-delete-dialog.component';
import { IServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';

@Injectable({ providedIn: 'root' })
export class ServiceSupportRoleContextTypeResolve implements Resolve<IServiceSupportRoleContextType> {
  constructor(private service: ServiceSupportRoleContextTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceSupportRoleContextType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceSupportRoleContextType>) => response.ok),
        map((serviceSupportRoleContextType: HttpResponse<ServiceSupportRoleContextType>) => serviceSupportRoleContextType.body)
      );
    }
    return of(new ServiceSupportRoleContextType());
  }
}

export const serviceSupportRoleContextTypeRoute: Routes = [
  {
    path: '',
    component: ServiceSupportRoleContextTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceSupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceSupportRoleContextTypeDetailComponent,
    resolve: {
      serviceSupportRoleContextType: ServiceSupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceSupportRoleContextTypeUpdateComponent,
    resolve: {
      serviceSupportRoleContextType: ServiceSupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceSupportRoleContextTypeUpdateComponent,
    resolve: {
      serviceSupportRoleContextType: ServiceSupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceSupportRoleContextTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceSupportRoleContextTypeDeletePopupComponent,
    resolve: {
      serviceSupportRoleContextType: ServiceSupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
