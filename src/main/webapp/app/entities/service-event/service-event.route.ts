import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceEvent } from 'app/shared/model/service-event.model';
import { ServiceEventService } from './service-event.service';
import { ServiceEventComponent } from './service-event.component';
import { ServiceEventDetailComponent } from './service-event-detail.component';
import { ServiceEventUpdateComponent } from './service-event-update.component';
import { ServiceEventDeletePopupComponent } from './service-event-delete-dialog.component';
import { IServiceEvent } from 'app/shared/model/service-event.model';

@Injectable({ providedIn: 'root' })
export class ServiceEventResolve implements Resolve<IServiceEvent> {
  constructor(private service: ServiceEventService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceEvent> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceEvent>) => response.ok),
        map((serviceEvent: HttpResponse<ServiceEvent>) => serviceEvent.body)
      );
    }
    return of(new ServiceEvent());
  }
}

export const serviceEventRoute: Routes = [
  {
    path: '',
    component: ServiceEventComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceEventDetailComponent,
    resolve: {
      serviceEvent: ServiceEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceEventUpdateComponent,
    resolve: {
      serviceEvent: ServiceEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceEventUpdateComponent,
    resolve: {
      serviceEvent: ServiceEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceEventPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceEventDeletePopupComponent,
    resolve: {
      serviceEvent: ServiceEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvent.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
