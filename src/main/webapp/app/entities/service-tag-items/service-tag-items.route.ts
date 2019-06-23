import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceTagItems } from 'app/shared/model/service-tag-items.model';
import { ServiceTagItemsService } from './service-tag-items.service';
import { ServiceTagItemsComponent } from './service-tag-items.component';
import { ServiceTagItemsDetailComponent } from './service-tag-items-detail.component';
import { ServiceTagItemsUpdateComponent } from './service-tag-items-update.component';
import { ServiceTagItemsDeletePopupComponent } from './service-tag-items-delete-dialog.component';
import { IServiceTagItems } from 'app/shared/model/service-tag-items.model';

@Injectable({ providedIn: 'root' })
export class ServiceTagItemsResolve implements Resolve<IServiceTagItems> {
  constructor(private service: ServiceTagItemsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceTagItems> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceTagItems>) => response.ok),
        map((serviceTagItems: HttpResponse<ServiceTagItems>) => serviceTagItems.body)
      );
    }
    return of(new ServiceTagItems());
  }
}

export const serviceTagItemsRoute: Routes = [
  {
    path: '',
    component: ServiceTagItemsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceTagItemsDetailComponent,
    resolve: {
      serviceTagItems: ServiceTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceTagItemsUpdateComponent,
    resolve: {
      serviceTagItems: ServiceTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceTagItemsUpdateComponent,
    resolve: {
      serviceTagItems: ServiceTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceTagItemsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceTagItemsDeletePopupComponent,
    resolve: {
      serviceTagItems: ServiceTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTagItems.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
