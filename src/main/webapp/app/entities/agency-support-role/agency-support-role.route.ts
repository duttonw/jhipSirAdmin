import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgencySupportRole } from 'app/shared/model/agency-support-role.model';
import { AgencySupportRoleService } from './agency-support-role.service';
import { AgencySupportRoleComponent } from './agency-support-role.component';
import { AgencySupportRoleDetailComponent } from './agency-support-role-detail.component';
import { AgencySupportRoleUpdateComponent } from './agency-support-role-update.component';
import { AgencySupportRoleDeletePopupComponent } from './agency-support-role-delete-dialog.component';
import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';

@Injectable({ providedIn: 'root' })
export class AgencySupportRoleResolve implements Resolve<IAgencySupportRole> {
  constructor(private service: AgencySupportRoleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgencySupportRole> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgencySupportRole>) => response.ok),
        map((agencySupportRole: HttpResponse<AgencySupportRole>) => agencySupportRole.body)
      );
    }
    return of(new AgencySupportRole());
  }
}

export const agencySupportRoleRoute: Routes = [
  {
    path: '',
    component: AgencySupportRoleComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.agencySupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgencySupportRoleDetailComponent,
    resolve: {
      agencySupportRole: AgencySupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgencySupportRoleUpdateComponent,
    resolve: {
      agencySupportRole: AgencySupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgencySupportRoleUpdateComponent,
    resolve: {
      agencySupportRole: AgencySupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRole.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agencySupportRolePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgencySupportRoleDeletePopupComponent,
    resolve: {
      agencySupportRole: AgencySupportRoleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRole.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
