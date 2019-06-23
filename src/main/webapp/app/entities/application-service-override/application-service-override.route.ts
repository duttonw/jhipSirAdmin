import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationServiceOverride } from 'app/shared/model/application-service-override.model';
import { ApplicationServiceOverrideService } from './application-service-override.service';
import { ApplicationServiceOverrideComponent } from './application-service-override.component';
import { ApplicationServiceOverrideDetailComponent } from './application-service-override-detail.component';
import { ApplicationServiceOverrideUpdateComponent } from './application-service-override-update.component';
import { ApplicationServiceOverrideDeletePopupComponent } from './application-service-override-delete-dialog.component';
import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideResolve implements Resolve<IApplicationServiceOverride> {
  constructor(private service: ApplicationServiceOverrideService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationServiceOverride> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApplicationServiceOverride>) => response.ok),
        map((applicationServiceOverride: HttpResponse<ApplicationServiceOverride>) => applicationServiceOverride.body)
      );
    }
    return of(new ApplicationServiceOverride());
  }
}

export const applicationServiceOverrideRoute: Routes = [
  {
    path: '',
    component: ApplicationServiceOverrideComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.applicationServiceOverride.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationServiceOverrideDetailComponent,
    resolve: {
      applicationServiceOverride: ApplicationServiceOverrideResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverride.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationServiceOverrideUpdateComponent,
    resolve: {
      applicationServiceOverride: ApplicationServiceOverrideResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverride.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationServiceOverrideUpdateComponent,
    resolve: {
      applicationServiceOverride: ApplicationServiceOverrideResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverride.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const applicationServiceOverridePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationServiceOverrideDeletePopupComponent,
    resolve: {
      applicationServiceOverride: ApplicationServiceOverrideResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverride.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
