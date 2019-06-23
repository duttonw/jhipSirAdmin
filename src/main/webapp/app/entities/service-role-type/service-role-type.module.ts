import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceRoleTypeComponent,
  ServiceRoleTypeDetailComponent,
  ServiceRoleTypeUpdateComponent,
  ServiceRoleTypeDeletePopupComponent,
  ServiceRoleTypeDeleteDialogComponent,
  serviceRoleTypeRoute,
  serviceRoleTypePopupRoute
} from './';

const ENTITY_STATES = [...serviceRoleTypeRoute, ...serviceRoleTypePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceRoleTypeComponent,
    ServiceRoleTypeDetailComponent,
    ServiceRoleTypeUpdateComponent,
    ServiceRoleTypeDeleteDialogComponent,
    ServiceRoleTypeDeletePopupComponent
  ],
  entryComponents: [
    ServiceRoleTypeComponent,
    ServiceRoleTypeUpdateComponent,
    ServiceRoleTypeDeleteDialogComponent,
    ServiceRoleTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceRoleTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
