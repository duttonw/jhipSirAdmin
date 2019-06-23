import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';
import { ServiceDeliveryOrganisationService } from './service-delivery-organisation.service';
import { ServiceDeliveryOrganisationComponent } from './service-delivery-organisation.component';
import { ServiceDeliveryOrganisationDetailComponent } from './service-delivery-organisation-detail.component';
import { ServiceDeliveryOrganisationUpdateComponent } from './service-delivery-organisation-update.component';
import { ServiceDeliveryOrganisationDeletePopupComponent } from './service-delivery-organisation-delete-dialog.component';
import { IServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';

@Injectable({ providedIn: 'root' })
export class ServiceDeliveryOrganisationResolve implements Resolve<IServiceDeliveryOrganisation> {
  constructor(private service: ServiceDeliveryOrganisationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceDeliveryOrganisation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceDeliveryOrganisation>) => response.ok),
        map((serviceDeliveryOrganisation: HttpResponse<ServiceDeliveryOrganisation>) => serviceDeliveryOrganisation.body)
      );
    }
    return of(new ServiceDeliveryOrganisation());
  }
}

export const serviceDeliveryOrganisationRoute: Routes = [
  {
    path: '',
    component: ServiceDeliveryOrganisationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceDeliveryOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceDeliveryOrganisationDetailComponent,
    resolve: {
      serviceDeliveryOrganisation: ServiceDeliveryOrganisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceDeliveryOrganisationUpdateComponent,
    resolve: {
      serviceDeliveryOrganisation: ServiceDeliveryOrganisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceDeliveryOrganisationUpdateComponent,
    resolve: {
      serviceDeliveryOrganisation: ServiceDeliveryOrganisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceDeliveryOrganisationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceDeliveryOrganisationDeletePopupComponent,
    resolve: {
      serviceDeliveryOrganisation: ServiceDeliveryOrganisationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDeliveryOrganisation.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
