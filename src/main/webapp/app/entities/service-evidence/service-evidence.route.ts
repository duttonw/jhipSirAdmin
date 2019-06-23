import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceEvidence } from 'app/shared/model/service-evidence.model';
import { ServiceEvidenceService } from './service-evidence.service';
import { ServiceEvidenceComponent } from './service-evidence.component';
import { ServiceEvidenceDetailComponent } from './service-evidence-detail.component';
import { ServiceEvidenceUpdateComponent } from './service-evidence-update.component';
import { ServiceEvidenceDeletePopupComponent } from './service-evidence-delete-dialog.component';
import { IServiceEvidence } from 'app/shared/model/service-evidence.model';

@Injectable({ providedIn: 'root' })
export class ServiceEvidenceResolve implements Resolve<IServiceEvidence> {
  constructor(private service: ServiceEvidenceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceEvidence> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceEvidence>) => response.ok),
        map((serviceEvidence: HttpResponse<ServiceEvidence>) => serviceEvidence.body)
      );
    }
    return of(new ServiceEvidence());
  }
}

export const serviceEvidenceRoute: Routes = [
  {
    path: '',
    component: ServiceEvidenceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.serviceEvidence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceEvidenceDetailComponent,
    resolve: {
      serviceEvidence: ServiceEvidenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvidence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceEvidenceUpdateComponent,
    resolve: {
      serviceEvidence: ServiceEvidenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvidence.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceEvidenceUpdateComponent,
    resolve: {
      serviceEvidence: ServiceEvidenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvidence.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceEvidencePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceEvidenceDeletePopupComponent,
    resolve: {
      serviceEvidence: ServiceEvidenceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.serviceEvidence.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
