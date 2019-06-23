import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IntegrationMapping } from 'app/shared/model/integration-mapping.model';
import { IntegrationMappingService } from './integration-mapping.service';
import { IntegrationMappingComponent } from './integration-mapping.component';
import { IntegrationMappingDetailComponent } from './integration-mapping-detail.component';
import { IntegrationMappingUpdateComponent } from './integration-mapping-update.component';
import { IntegrationMappingDeletePopupComponent } from './integration-mapping-delete-dialog.component';
import { IIntegrationMapping } from 'app/shared/model/integration-mapping.model';

@Injectable({ providedIn: 'root' })
export class IntegrationMappingResolve implements Resolve<IIntegrationMapping> {
  constructor(private service: IntegrationMappingService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIntegrationMapping> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IntegrationMapping>) => response.ok),
        map((integrationMapping: HttpResponse<IntegrationMapping>) => integrationMapping.body)
      );
    }
    return of(new IntegrationMapping());
  }
}

export const integrationMappingRoute: Routes = [
  {
    path: '',
    component: IntegrationMappingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.integrationMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IntegrationMappingDetailComponent,
    resolve: {
      integrationMapping: IntegrationMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.integrationMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IntegrationMappingUpdateComponent,
    resolve: {
      integrationMapping: IntegrationMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.integrationMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IntegrationMappingUpdateComponent,
    resolve: {
      integrationMapping: IntegrationMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.integrationMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const integrationMappingPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IntegrationMappingDeletePopupComponent,
    resolve: {
      integrationMapping: IntegrationMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.integrationMapping.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
