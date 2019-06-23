import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LocationEmail } from 'app/shared/model/location-email.model';
import { LocationEmailService } from './location-email.service';
import { LocationEmailComponent } from './location-email.component';
import { LocationEmailDetailComponent } from './location-email-detail.component';
import { LocationEmailUpdateComponent } from './location-email-update.component';
import { LocationEmailDeletePopupComponent } from './location-email-delete-dialog.component';
import { ILocationEmail } from 'app/shared/model/location-email.model';

@Injectable({ providedIn: 'root' })
export class LocationEmailResolve implements Resolve<ILocationEmail> {
  constructor(private service: LocationEmailService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILocationEmail> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LocationEmail>) => response.ok),
        map((locationEmail: HttpResponse<LocationEmail>) => locationEmail.body)
      );
    }
    return of(new LocationEmail());
  }
}

export const locationEmailRoute: Routes = [
  {
    path: '',
    component: LocationEmailComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.locationEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocationEmailDetailComponent,
    resolve: {
      locationEmail: LocationEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocationEmailUpdateComponent,
    resolve: {
      locationEmail: LocationEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocationEmailUpdateComponent,
    resolve: {
      locationEmail: LocationEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationEmail.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const locationEmailPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LocationEmailDeletePopupComponent,
    resolve: {
      locationEmail: LocationEmailResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.locationEmail.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
