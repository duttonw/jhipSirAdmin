import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceSupportRoleContextTypeComponent,
  ServiceSupportRoleContextTypeDetailComponent,
  ServiceSupportRoleContextTypeUpdateComponent,
  ServiceSupportRoleContextTypeDeletePopupComponent,
  ServiceSupportRoleContextTypeDeleteDialogComponent,
  serviceSupportRoleContextTypeRoute,
  serviceSupportRoleContextTypePopupRoute
} from './';

const ENTITY_STATES = [...serviceSupportRoleContextTypeRoute, ...serviceSupportRoleContextTypePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceSupportRoleContextTypeComponent,
    ServiceSupportRoleContextTypeDetailComponent,
    ServiceSupportRoleContextTypeUpdateComponent,
    ServiceSupportRoleContextTypeDeleteDialogComponent,
    ServiceSupportRoleContextTypeDeletePopupComponent
  ],
  entryComponents: [
    ServiceSupportRoleContextTypeComponent,
    ServiceSupportRoleContextTypeUpdateComponent,
    ServiceSupportRoleContextTypeDeleteDialogComponent,
    ServiceSupportRoleContextTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceSupportRoleContextTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
