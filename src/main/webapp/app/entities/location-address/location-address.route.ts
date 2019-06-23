import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LocationAddress } from 'app/shared/model/location-address.model';
import { LocationAddressService } from './location-address.service';
import { LocationAddressComponent } from './location-address.component';
import { LocationAddressDetailComponent } from './location-address-detail.component';
import { LocationAddressUpdateComponent } from './location-address-update.component';
import { LocationAddressDeletePopupComponent } from './location-address-delete-dialog.component';
import { ILocationAddress } from 'app/shared/model/location-address.model';

@Injectable({ providedIn: 'root' })
export class LocationAddressResolve implements Resolve<ILocationAddress> {
  constructor(private service: LocationAddressService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocationAddress> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LocationAddress>) => response.ok),
        map((locationAddress: HttpResponse<LocationAddress>) => locationAddress.body)
      );
    }
    return of(new LocationAddress());
  }
}

export const locationAddressRoute: Routes = [
  {
    path: '',
    component: LocationAddressComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.locationAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocationAddressDetailComponent,
    resolve: {
      locationAddress: LocationAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocationAddressUpdateComponent,
    resolve: {
      locationAddress: LocationAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocationAddressUpdateComponent,
    resolve: {
      locationAddress: LocationAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationAddress.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const locationAddressPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocationAddressDeletePopupComponent,
    resolve: {
      locationAddress: LocationAddressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationAddress.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
