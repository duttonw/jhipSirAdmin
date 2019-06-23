import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceGroup } from 'app/shared/model/service-group.model';
import { ServiceGroupService } from './service-group.service';
import { ServiceGroupComponent } from './service-group.component';
import { ServiceGroupDetailComponent } from './service-group-detail.component';
import { ServiceGroupUpdateComponent } from './service-group-update.component';
import { ServiceGroupDeletePopupComponent } from './service-group-delete-dialog.component';
import { IServiceGroup } from 'app/shared/model/service-group.model';

@Injectable({ providedIn: 'root' })
export class ServiceGroupResolve implements Resolve<IServiceGroup> {
  constructor(private service: ServiceGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceGroup>) => response.ok),
        map((serviceGroup: HttpResponse<ServiceGroup>) => serviceGroup.body)
      );
    }
    return of(new ServiceGroup());
  }
}

export const serviceGroupRoute: Routes = [
  {
    path: '',
    component: ServiceGroupComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceGroupDetailComponent,
    resolve: {
      serviceGroup: ServiceGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceGroupUpdateComponent,
    resolve: {
      serviceGroup: ServiceGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceGroupUpdateComponent,
    resolve: {
      serviceGroup: ServiceGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceGroupDeletePopupComponent,
    resolve: {
      serviceGroup: ServiceGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceGroup.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
