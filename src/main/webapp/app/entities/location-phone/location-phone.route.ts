import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LocationPhone } from 'app/shared/model/location-phone.model';
import { LocationPhoneService } from './location-phone.service';
import { LocationPhoneComponent } from './location-phone.component';
import { LocationPhoneDetailComponent } from './location-phone-detail.component';
import { LocationPhoneUpdateComponent } from './location-phone-update.component';
import { LocationPhoneDeletePopupComponent } from './location-phone-delete-dialog.component';
import { ILocationPhone } from 'app/shared/model/location-phone.model';

@Injectable({ providedIn: 'root' })
export class LocationPhoneResolve implements Resolve<ILocationPhone> {
  constructor(private service: LocationPhoneService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocationPhone> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LocationPhone>) => response.ok),
        map((locationPhone: HttpResponse<LocationPhone>) => locationPhone.body)
      );
    }
    return of(new LocationPhone());
  }
}

export const locationPhoneRoute: Routes = [
  {
    path: '',
    component: LocationPhoneComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.locationPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocationPhoneDetailComponent,
    resolve: {
      locationPhone: LocationPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocationPhoneUpdateComponent,
    resolve: {
      locationPhone: LocationPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocationPhoneUpdateComponent,
    resolve: {
      locationPhone: LocationPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationPhone.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const locationPhonePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocationPhoneDeletePopupComponent,
    resolve: {
      locationPhone: LocationPhoneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationPhone.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
