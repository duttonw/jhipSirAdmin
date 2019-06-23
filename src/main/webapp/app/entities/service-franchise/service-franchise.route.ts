import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceFranchise } from 'app/shared/model/service-franchise.model';
import { ServiceFranchiseService } from './service-franchise.service';
import { ServiceFranchiseComponent } from './service-franchise.component';
import { ServiceFranchiseDetailComponent } from './service-franchise-detail.component';
import { ServiceFranchiseUpdateComponent } from './service-franchise-update.component';
import { ServiceFranchiseDeletePopupComponent } from './service-franchise-delete-dialog.component';
import { IServiceFranchise } from 'app/shared/model/service-franchise.model';

@Injectable({ providedIn: 'root' })
export class ServiceFranchiseResolve implements Resolve<IServiceFranchise> {
  constructor(private service: ServiceFranchiseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceFranchise> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceFranchise>) => response.ok),
        map((serviceFranchise: HttpResponse<ServiceFranchise>) => serviceFranchise.body)
      );
    }
    return of(new ServiceFranchise());
  }
}

export const serviceFranchiseRoute: Routes = [
  {
    path: '',
    component: ServiceFranchiseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceFranchise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceFranchiseDetailComponent,
    resolve: {
      serviceFranchise: ServiceFranchiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceFranchise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceFranchiseUpdateComponent,
    resolve: {
      serviceFranchise: ServiceFranchiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceFranchise.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceFranchiseUpdateComponent,
    resolve: {
      serviceFranchise: ServiceFranchiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceFranchise.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceFranchisePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceFranchiseDeletePopupComponent,
    resolve: {
      serviceFranchise: ServiceFranchiseResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceFranchise.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
