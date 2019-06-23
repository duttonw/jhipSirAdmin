import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceSupportRoleComponent,
  ServiceSupportRoleDetailComponent,
  ServiceSupportRoleUpdateComponent,
  ServiceSupportRoleDeletePopupComponent,
  ServiceSupportRoleDeleteDialogComponent,
  serviceSupportRoleRoute,
  serviceSupportRolePopupRoute
} from './';

const ENTITY_STATES = [...serviceSupportRoleRoute, ...serviceSupportRolePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceSupportRoleComponent,
    ServiceSupportRoleDetailComponent,
    ServiceSupportRoleUpdateComponent,
    ServiceSupportRoleDeleteDialogComponent,
    ServiceSupportRoleDeletePopupComponent
  ],
  entryComponents: [
    ServiceSupportRoleComponent,
    ServiceSupportRoleUpdateComponent,
    ServiceSupportRoleDeleteDialogComponent,
    ServiceSupportRoleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceSupportRoleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
