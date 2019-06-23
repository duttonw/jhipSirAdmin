import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceDelivery } from 'app/shared/model/service-delivery.model';
import { ServiceDeliveryService } from './service-delivery.service';
import { ServiceDeliveryComponent } from './service-delivery.component';
import { ServiceDeliveryDetailComponent } from './service-delivery-detail.component';
import { ServiceDeliveryUpdateComponent } from './service-delivery-update.component';
import { ServiceDeliveryDeletePopupComponent } from './service-delivery-delete-dialog.component';
import { IServiceDelivery } from 'app/shared/model/service-delivery.model';

@Injectable({ providedIn: 'root' })
export class ServiceDeliveryResolve implements Resolve<IServiceDelivery> {
  constructor(private service: ServiceDeliveryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceDelivery> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceDelivery>) => response.ok),
        map((serviceDelivery: HttpResponse<ServiceDelivery>) => serviceDelivery.body)
      );
    }
    return of(new ServiceDelivery());
  }
}

export const serviceDeliveryRoute: Routes = [
  {
    path: '',
    component: ServiceDeliveryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceDeliveryDetailComponent,
    resolve: {
      serviceDelivery: ServiceDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceDeliveryUpdateComponent,
    resolve: {
      serviceDelivery: ServiceDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceDeliveryUpdateComponent,
    resolve: {
      serviceDelivery: ServiceDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDelivery.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceDeliveryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceDeliveryDeletePopupComponent,
    resolve: {
      serviceDelivery: ServiceDeliveryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDelivery.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
