import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceSupportRole } from 'app/shared/model/service-support-role.model';
import { ServiceSupportRoleService } from './service-support-role.service';
import { ServiceSupportRoleComponent } from './service-support-role.component';
import { ServiceSupportRoleDetailComponent } from './service-support-role-detail.component';
import { ServiceSupportRoleUpdateComponent } from './service-support-role-update.component';
import { ServiceSupportRoleDeletePopupComponent } from './service-support-role-delete-dialog.component';
import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';

@Injectable({ providedIn: 'root' })
export class ServiceSupportRoleResolve implements Resolve<IServiceSupportRole> {
  constructor(private service: ServiceSupportRoleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceSupportRole> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceSupportRole>) => response.ok),
        map((serviceSupportRole: HttpResponse<ServiceSupportRole>) => serviceSupportRole.body)
      );
    }
    return of(new ServiceSupportRole());
  }
}

export const serviceSupportRoleRoute: Routes = [
  {
    path: '',
    component: ServiceSupportRoleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceSupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceSupportRoleDetailComponent,
    resolve: {
      serviceSupportRole: ServiceSupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceSupportRoleUpdateComponent,
    resolve: {
      serviceSupportRole: ServiceSupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceSupportRoleUpdateComponent,
    resolve: {
      serviceSupportRole: ServiceSupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceSupportRolePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceSupportRoleDeletePopupComponent,
    resolve: {
      serviceSupportRole: ServiceSupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceSupportRole.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
