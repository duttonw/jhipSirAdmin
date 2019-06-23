import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AvailabilityHours } from 'app/shared/model/availability-hours.model';
import { AvailabilityHoursService } from './availability-hours.service';
import { AvailabilityHoursComponent } from './availability-hours.component';
import { AvailabilityHoursDetailComponent } from './availability-hours-detail.component';
import { AvailabilityHoursUpdateComponent } from './availability-hours-update.component';
import { AvailabilityHoursDeletePopupComponent } from './availability-hours-delete-dialog.component';
import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';

@Injectable({ providedIn: 'root' })
export class AvailabilityHoursResolve implements Resolve<IAvailabilityHours> {
  constructor(private service: AvailabilityHoursService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAvailabilityHours> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AvailabilityHours>) => response.ok),
        map((availabilityHours: HttpResponse<AvailabilityHours>) => availabilityHours.body)
      );
    }
    return of(new AvailabilityHours());
  }
}

export const availabilityHoursRoute: Routes = [
  {
    path: '',
    component: AvailabilityHoursComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.availabilityHours.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AvailabilityHoursDetailComponent,
    resolve: {
      availabilityHours: AvailabilityHoursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.availabilityHours.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AvailabilityHoursUpdateComponent,
    resolve: {
      availabilityHours: AvailabilityHoursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.availabilityHours.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AvailabilityHoursUpdateComponent,
    resolve: {
      availabilityHours: AvailabilityHoursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.availabilityHours.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const availabilityHoursPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AvailabilityHoursDeletePopupComponent,
    resolve: {
      availabilityHours: AvailabilityHoursResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.availabilityHours.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
