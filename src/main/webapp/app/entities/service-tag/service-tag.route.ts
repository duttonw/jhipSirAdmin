import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceTag } from 'app/shared/model/service-tag.model';
import { ServiceTagService } from './service-tag.service';
import { ServiceTagComponent } from './service-tag.component';
import { ServiceTagDetailComponent } from './service-tag-detail.component';
import { ServiceTagUpdateComponent } from './service-tag-update.component';
import { ServiceTagDeletePopupComponent } from './service-tag-delete-dialog.component';
import { IServiceTag } from 'app/shared/model/service-tag.model';

@Injectable({ providedIn: 'root' })
export class ServiceTagResolve implements Resolve<IServiceTag> {
  constructor(private service: ServiceTagService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceTag> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceTag>) => response.ok),
        map((serviceTag: HttpResponse<ServiceTag>) => serviceTag.body)
      );
    }
    return of(new ServiceTag());
  }
}

export const serviceTagRoute: Routes = [
  {
    path: '',
    component: ServiceTagComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceTagDetailComponent,
    resolve: {
      serviceTag: ServiceTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceTagUpdateComponent,
    resolve: {
      serviceTag: ServiceTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceTagUpdateComponent,
    resolve: {
      serviceTag: ServiceTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTag.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceTagPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceTagDeletePopupComponent,
    resolve: {
      serviceTag: ServiceTagResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceTag.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
