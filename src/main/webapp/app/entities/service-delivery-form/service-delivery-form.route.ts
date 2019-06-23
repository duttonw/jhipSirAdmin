import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';
import { ServiceDeliveryFormService } from './service-delivery-form.service';
import { ServiceDeliveryFormComponent } from './service-delivery-form.component';
import { ServiceDeliveryFormDetailComponent } from './service-delivery-form-detail.component';
import { ServiceDeliveryFormUpdateComponent } from './service-delivery-form-update.component';
import { ServiceDeliveryFormDeletePopupComponent } from './service-delivery-form-delete-dialog.component';
import { IServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';

@Injectable({ providedIn: 'root' })
export class ServiceDeliveryFormResolve implements Resolve<IServiceDeliveryForm> {
  constructor(private service: ServiceDeliveryFormService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceDeliveryForm> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceDeliveryForm>) => response.ok),
        map((serviceDeliveryForm: HttpResponse<ServiceDeliveryForm>) => serviceDeliveryForm.body)
      );
    }
    return of(new ServiceDeliveryForm());
  }
}

export const serviceDeliveryFormRoute: Routes = [
  {
    path: '',
    component: ServiceDeliveryFormComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceDeliveryForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceDeliveryFormDetailComponent,
    resolve: {
      serviceDeliveryForm: ServiceDeliveryFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceDeliveryFormUpdateComponent,
    resolve: {
      serviceDeliveryForm: ServiceDeliveryFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceDeliveryFormUpdateComponent,
    resolve: {
      serviceDeliveryForm: ServiceDeliveryFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryForm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceDeliveryFormPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceDeliveryFormDeletePopupComponent,
    resolve: {
      serviceDeliveryForm: ServiceDeliveryFormResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryForm.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
