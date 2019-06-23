import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceDescription } from 'app/shared/model/service-description.model';
import { ServiceDescriptionService } from './service-description.service';
import { ServiceDescriptionComponent } from './service-description.component';
import { ServiceDescriptionDetailComponent } from './service-description-detail.component';
import { ServiceDescriptionUpdateComponent } from './service-description-update.component';
import { ServiceDescriptionDeletePopupComponent } from './service-description-delete-dialog.component';
import { IServiceDescription } from 'app/shared/model/service-description.model';

@Injectable({ providedIn: 'root' })
export class ServiceDescriptionResolve implements Resolve<IServiceDescription> {
  constructor(private service: ServiceDescriptionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceDescription> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceDescription>) => response.ok),
        map((serviceDescription: HttpResponse<ServiceDescription>) => serviceDescription.body)
      );
    }
    return of(new ServiceDescription());
  }
}

export const serviceDescriptionRoute: Routes = [
  {
    path: '',
    component: ServiceDescriptionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceDescriptionDetailComponent,
    resolve: {
      serviceDescription: ServiceDescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceDescriptionUpdateComponent,
    resolve: {
      serviceDescription: ServiceDescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceDescriptionUpdateComponent,
    resolve: {
      serviceDescription: ServiceDescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDescription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceDescriptionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceDescriptionDeletePopupComponent,
    resolve: {
      serviceDescription: ServiceDescriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceDescription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
