import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Agency } from 'app/shared/model/agency.model';
import { AgencyService } from './agency.service';
import { AgencyComponent } from './agency.component';
import { AgencyDetailComponent } from './agency-detail.component';
import { AgencyUpdateComponent } from './agency-update.component';
import { AgencyDeletePopupComponent } from './agency-delete-dialog.component';
import { IAgency } from 'app/shared/model/agency.model';

@Injectable({ providedIn: 'root' })
export class AgencyResolve implements Resolve<IAgency> {
  constructor(private service: AgencyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAgency> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Agency>) => response.ok),
        map((agency: HttpResponse<Agency>) => agency.body)
      );
    }
    return of(new Agency());
  }
}

export const agencyRoute: Routes = [
  {
    path: '',
    component: AgencyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.agency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgencyDetailComponent,
    resolve: {
      agency: AgencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgencyUpdateComponent,
    resolve: {
      agency: AgencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agency.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgencyUpdateComponent,
    resolve: {
      agency: AgencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agency.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const agencyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AgencyDeletePopupComponent,
    resolve: {
      agency: AgencyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.agency.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
