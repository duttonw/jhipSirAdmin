import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';
import { ApplicationServiceOverrideAttributeService } from './application-service-override-attribute.service';
import { ApplicationServiceOverrideAttributeComponent } from './application-service-override-attribute.component';
import { ApplicationServiceOverrideAttributeDetailComponent } from './application-service-override-attribute-detail.component';
import { ApplicationServiceOverrideAttributeUpdateComponent } from './application-service-override-attribute-update.component';
import { ApplicationServiceOverrideAttributeDeletePopupComponent } from './application-service-override-attribute-delete-dialog.component';
import { IApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideAttributeResolve implements Resolve<IApplicationServiceOverrideAttribute> {
  constructor(private service: ApplicationServiceOverrideAttributeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationServiceOverrideAttribute> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApplicationServiceOverrideAttribute>) => response.ok),
        map(
          (applicationServiceOverrideAttribute: HttpResponse<ApplicationServiceOverrideAttribute>) =>
            applicationServiceOverrideAttribute.body
        )
      );
    }
    return of(new ApplicationServiceOverrideAttribute());
  }
}

export const applicationServiceOverrideAttributeRoute: Routes = [
  {
    path: '',
    component: ApplicationServiceOverrideAttributeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationServiceOverrideAttributeDetailComponent,
    resolve: {
      applicationServiceOverrideAttribute: ApplicationServiceOverrideAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationServiceOverrideAttributeUpdateComponent,
    resolve: {
      applicationServiceOverrideAttribute: ApplicationServiceOverrideAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationServiceOverrideAttributeUpdateComponent,
    resolve: {
      applicationServiceOverrideAttribute: ApplicationServiceOverrideAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideAttribute.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const applicationServiceOverrideAttributePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationServiceOverrideAttributeDeletePopupComponent,
    resolve: {
      applicationServiceOverrideAttribute: ApplicationServiceOverrideAttributeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideAttribute.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
