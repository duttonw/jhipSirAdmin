import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceName } from 'app/shared/model/service-name.model';
import { ServiceNameService } from './service-name.service';
import { ServiceNameComponent } from './service-name.component';
import { ServiceNameDetailComponent } from './service-name-detail.component';
import { ServiceNameUpdateComponent } from './service-name-update.component';
import { ServiceNameDeletePopupComponent } from './service-name-delete-dialog.component';
import { IServiceName } from 'app/shared/model/service-name.model';

@Injectable({ providedIn: 'root' })
export class ServiceNameResolve implements Resolve<IServiceName> {
  constructor(private service: ServiceNameService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceName> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceName>) => response.ok),
        map((serviceName: HttpResponse<ServiceName>) => serviceName.body)
      );
    }
    return of(new ServiceName());
  }
}

export const serviceNameRoute: Routes = [
  {
    path: '',
    component: ServiceNameComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceName.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceNameDetailComponent,
    resolve: {
      serviceName: ServiceNameResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceName.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceNameUpdateComponent,
    resolve: {
      serviceName: ServiceNameResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceName.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceNameUpdateComponent,
    resolve: {
      serviceName: ServiceNameResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceName.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceNamePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceNameDeletePopupComponent,
    resolve: {
      serviceName: ServiceNameResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceName.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
