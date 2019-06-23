import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';
import { OpeningHoursSpecificationService } from './opening-hours-specification.service';
import { OpeningHoursSpecificationComponent } from './opening-hours-specification.component';
import { OpeningHoursSpecificationDetailComponent } from './opening-hours-specification-detail.component';
import { OpeningHoursSpecificationUpdateComponent } from './opening-hours-specification-update.component';
import { OpeningHoursSpecificationDeletePopupComponent } from './opening-hours-specification-delete-dialog.component';
import { IOpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';

@Injectable({ providedIn: 'root' })
export class OpeningHoursSpecificationResolve implements Resolve<IOpeningHoursSpecification> {
  constructor(private service: OpeningHoursSpecificationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOpeningHoursSpecification> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OpeningHoursSpecification>) => response.ok),
        map((openingHoursSpecification: HttpResponse<OpeningHoursSpecification>) => openingHoursSpecification.body)
      );
    }
    return of(new OpeningHoursSpecification());
  }
}

export const openingHoursSpecificationRoute: Routes = [
  {
    path: '',
    component: OpeningHoursSpecificationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'jhipSirAdminApp.openingHoursSpecification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OpeningHoursSpecificationDetailComponent,
    resolve: {
      openingHoursSpecification: OpeningHoursSpecificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.openingHoursSpecification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OpeningHoursSpecificationUpdateComponent,
    resolve: {
      openingHoursSpecification: OpeningHoursSpecificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.openingHoursSpecification.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OpeningHoursSpecificationUpdateComponent,
    resolve: {
      openingHoursSpecification: OpeningHoursSpecificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.openingHoursSpecification.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const openingHoursSpecificationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OpeningHoursSpecificationDeletePopupComponent,
    resolve: {
      openingHoursSpecification: OpeningHoursSpecificationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'jhipSirAdminApp.openingHoursSpecification.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
