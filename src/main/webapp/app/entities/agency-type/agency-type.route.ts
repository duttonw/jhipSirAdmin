import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AgencyType } from 'app/shared/model/agency-type.model';
import { AgencyTypeService } from './agency-type.service';
import { AgencyTypeComponent } from './agency-type.component';
import { AgencyTypeDetailComponent } from './agency-type-detail.component';
import { AgencyTypeUpdateComponent } from './agency-type-update.component';
import { AgencyTypeDeletePopupComponent } from './agency-type-delete-dialog.component';
import { IAgencyType } from 'app/shared/model/agency-type.model';

@Injectable({ providedIn: 'root' })
export class AgencyTypeResolve implements Resolve<IAgencyType> {
  constructor(private service: AgencyTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgencyType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AgencyType>) => response.ok),
        map((agencyType: HttpResponse<AgencyType>) => agencyType.body)
      );
    }
    return of(new AgencyType());
  }
}

export const agencyTypeRoute: Routes = [
  {
    path: '',
    component: AgencyTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.agencyType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgencyTypeDetailComponent,
    resolve: {
      agencyType: AgencyTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencyType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgencyTypeUpdateComponent,
    resolve: {
      agencyType: AgencyTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencyType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgencyTypeUpdateComponent,
    resolve: {
      agencyType: AgencyTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencyType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agencyTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgencyTypeDeletePopupComponent,
    resolve: {
      agencyType: AgencyTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agencyType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
