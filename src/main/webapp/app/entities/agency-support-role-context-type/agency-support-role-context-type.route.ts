import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';
import { AgencySupportRoleContextTypeService } from './agency-support-role-context-type.service';
import { AgencySupportRoleContextTypeComponent } from './agency-support-role-context-type.component';
import { AgencySupportRoleContextTypeDetailComponent } from './agency-support-role-context-type-detail.component';
import { AgencySupportRoleContextTypeUpdateComponent } from './agency-support-role-context-type-update.component';
import { AgencySupportRoleContextTypeDeletePopupComponent } from './agency-support-role-context-type-delete-dialog.component';
import { IAgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';

@Injectable({ providedIn: 'root' })
export class AgencySupportRoleContextTypeResolve implements Resolve<IAgencySupportRoleContextType> {
  constructor(private service: AgencySupportRoleContextTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgencySupportRoleContextType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgencySupportRoleContextType>) => response.ok),
        map((agencySupportRoleContextType: HttpResponse<AgencySupportRoleContextType>) => agencySupportRoleContextType.body)
      );
    }
    return of(new AgencySupportRoleContextType());
  }
}

export const agencySupportRoleContextTypeRoute: Routes = [
  {
    path: '',
    component: AgencySupportRoleContextTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.agencySupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgencySupportRoleContextTypeDetailComponent,
    resolve: {
      agencySupportRoleContextType: AgencySupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgencySupportRoleContextTypeUpdateComponent,
    resolve: {
      agencySupportRoleContextType: AgencySupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgencySupportRoleContextTypeUpdateComponent,
    resolve: {
      agencySupportRoleContextType: AgencySupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agencySupportRoleContextTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgencySupportRoleContextTypeDeletePopupComponent,
    resolve: {
      agencySupportRoleContextType: AgencySupportRoleContextTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencySupportRoleContextType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
