import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';
import { LocationTypeComponent } from './location-type.component';
import { LocationTypeDetailComponent } from './location-type-detail.component';
import { LocationTypeUpdateComponent } from './location-type-update.component';
import { LocationTypeDeletePopupComponent } from './location-type-delete-dialog.component';
import { ILocationType } from 'app/shared/model/location-type.model';

@Injectable({ providedIn: 'root' })
export class LocationTypeResolve implements Resolve<ILocationType> {
  constructor(private service: LocationTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocationType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LocationType>) => response.ok),
        map((locationType: HttpResponse<LocationType>) => locationType.body)
      );
    }
    return of(new LocationType());
  }
}

export const locationTypeRoute: Routes = [
  {
    path: '',
    component: LocationTypeComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.locationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocationTypeDetailComponent,
    resolve: {
      locationType: LocationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocationTypeUpdateComponent,
    resolve: {
      locationType: LocationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocationTypeUpdateComponent,
    resolve: {
      locationType: LocationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const locationTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocationTypeDeletePopupComponent,
    resolve: {
      locationType: LocationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
