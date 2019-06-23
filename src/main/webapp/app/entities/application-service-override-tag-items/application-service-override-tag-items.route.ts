import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';
import { ApplicationServiceOverrideTagItemsService } from './application-service-override-tag-items.service';
import { ApplicationServiceOverrideTagItemsComponent } from './application-service-override-tag-items.component';
import { ApplicationServiceOverrideTagItemsDetailComponent } from './application-service-override-tag-items-detail.component';
import { ApplicationServiceOverrideTagItemsUpdateComponent } from './application-service-override-tag-items-update.component';
import { ApplicationServiceOverrideTagItemsDeletePopupComponent } from './application-service-override-tag-items-delete-dialog.component';
import { IApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideTagItemsResolve implements Resolve<IApplicationServiceOverrideTagItems> {
  constructor(private service: ApplicationServiceOverrideTagItemsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationServiceOverrideTagItems> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApplicationServiceOverrideTagItems>) => response.ok),
        map(
          (applicationServiceOverrideTagItems: HttpResponse<ApplicationServiceOverrideTagItems>) => applicationServiceOverrideTagItems.body
        )
      );
    }
    return of(new ApplicationServiceOverrideTagItems());
  }
}

export const applicationServiceOverrideTagItemsRoute: Routes = [
  {
    path: '',
    component: ApplicationServiceOverrideTagItemsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationServiceOverrideTagItemsDetailComponent,
    resolve: {
      applicationServiceOverrideTagItems: ApplicationServiceOverrideTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationServiceOverrideTagItemsUpdateComponent,
    resolve: {
      applicationServiceOverrideTagItems: ApplicationServiceOverrideTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationServiceOverrideTagItemsUpdateComponent,
    resolve: {
      applicationServiceOverrideTagItems: ApplicationServiceOverrideTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTagItems.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const applicationServiceOverrideTagItemsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationServiceOverrideTagItemsDeletePopupComponent,
    resolve: {
      applicationServiceOverrideTagItems: ApplicationServiceOverrideTagItemsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTagItems.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
