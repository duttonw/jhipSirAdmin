import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';
import { ApplicationServiceOverrideTagService } from './application-service-override-tag.service';
import { ApplicationServiceOverrideTagComponent } from './application-service-override-tag.component';
import { ApplicationServiceOverrideTagDetailComponent } from './application-service-override-tag-detail.component';
import { ApplicationServiceOverrideTagUpdateComponent } from './application-service-override-tag-update.component';
import { ApplicationServiceOverrideTagDeletePopupComponent } from './application-service-override-tag-delete-dialog.component';
import { IApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';

@Injectable({ providedIn: 'root' })
export class ApplicationServiceOverrideTagResolve implements Resolve<IApplicationServiceOverrideTag> {
  constructor(private service: ApplicationServiceOverrideTagService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IApplicationServiceOverrideTag> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ApplicationServiceOverrideTag>) => response.ok),
        map((applicationServiceOverrideTag: HttpResponse<ApplicationServiceOverrideTag>) => applicationServiceOverrideTag.body)
      );
    }
    return of(new ApplicationServiceOverrideTag());
  }
}

export const applicationServiceOverrideTagRoute: Routes = [
  {
    path: '',
    component: ApplicationServiceOverrideTagComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ApplicationServiceOverrideTagDetailComponent,
    resolve: {
      applicationServiceOverrideTag: ApplicationServiceOverrideTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ApplicationServiceOverrideTagUpdateComponent,
    resolve: {
      applicationServiceOverrideTag: ApplicationServiceOverrideTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ApplicationServiceOverrideTagUpdateComponent,
    resolve: {
      applicationServiceOverrideTag: ApplicationServiceOverrideTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const applicationServiceOverrideTagPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ApplicationServiceOverrideTagDeletePopupComponent,
    resolve: {
      applicationServiceOverrideTag: ApplicationServiceOverrideTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.applicationServiceOverrideTag.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
