import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceRelationship } from 'app/shared/model/service-relationship.model';
import { ServiceRelationshipService } from './service-relationship.service';
import { ServiceRelationshipComponent } from './service-relationship.component';
import { ServiceRelationshipDetailComponent } from './service-relationship-detail.component';
import { ServiceRelationshipUpdateComponent } from './service-relationship-update.component';
import { ServiceRelationshipDeletePopupComponent } from './service-relationship-delete-dialog.component';
import { IServiceRelationship } from 'app/shared/model/service-relationship.model';

@Injectable({ providedIn: 'root' })
export class ServiceRelationshipResolve implements Resolve<IServiceRelationship> {
  constructor(private service: ServiceRelationshipService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceRelationship> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceRelationship>) => response.ok),
        map((serviceRelationship: HttpResponse<ServiceRelationship>) => serviceRelationship.body)
      );
    }
    return of(new ServiceRelationship());
  }
}

export const serviceRelationshipRoute: Routes = [
  {
    path: '',
    component: ServiceRelationshipComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceRelationshipDetailComponent,
    resolve: {
      serviceRelationship: ServiceRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceRelationshipUpdateComponent,
    resolve: {
      serviceRelationship: ServiceRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceRelationshipUpdateComponent,
    resolve: {
      serviceRelationship: ServiceRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceRelationshipPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceRelationshipDeletePopupComponent,
    resolve: {
      serviceRelationship: ServiceRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceRelationship.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
