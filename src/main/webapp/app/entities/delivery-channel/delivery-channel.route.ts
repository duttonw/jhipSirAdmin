import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DeliveryChannel } from 'app/shared/model/delivery-channel.model';
import { DeliveryChannelService } from './delivery-channel.service';
import { DeliveryChannelComponent } from './delivery-channel.component';
import { DeliveryChannelDetailComponent } from './delivery-channel-detail.component';
import { DeliveryChannelUpdateComponent } from './delivery-channel-update.component';
import { DeliveryChannelDeletePopupComponent } from './delivery-channel-delete-dialog.component';
import { IDeliveryChannel } from 'app/shared/model/delivery-channel.model';

@Injectable({ providedIn: 'root' })
export class DeliveryChannelResolve implements Resolve<IDeliveryChannel> {
  constructor(private service: DeliveryChannelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDeliveryChannel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DeliveryChannel>) => response.ok),
        map((deliveryChannel: HttpResponse<DeliveryChannel>) => deliveryChannel.body)
      );
    }
    return of(new DeliveryChannel());
  }
}

export const deliveryChannelRoute: Routes = [
  {
    path: '',
    component: DeliveryChannelComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.deliveryChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeliveryChannelDetailComponent,
    resolve: {
      deliveryChannel: DeliveryChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.deliveryChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeliveryChannelUpdateComponent,
    resolve: {
      deliveryChannel: DeliveryChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.deliveryChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeliveryChannelUpdateComponent,
    resolve: {
      deliveryChannel: DeliveryChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.deliveryChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const deliveryChannelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DeliveryChannelDeletePopupComponent,
    resolve: {
      deliveryChannel: DeliveryChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.deliveryChannel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
