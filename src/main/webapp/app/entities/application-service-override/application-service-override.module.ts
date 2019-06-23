import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ApplicationServiceOverrideComponent,
  ApplicationServiceOverrideDetailComponent,
  ApplicationServiceOverrideUpdateComponent,
  ApplicationServiceOverrideDeletePopupComponent,
  ApplicationServiceOverrideDeleteDialogComponent,
  applicationServiceOverrideRoute,
  applicationServiceOverridePopupRoute
} from './';

const ENTITY_STATES = [...applicationServiceOverrideRoute, ...applicationServiceOverridePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationServiceOverrideComponent,
    ApplicationServiceOverrideDetailComponent,
    ApplicationServiceOverrideUpdateComponent,
    ApplicationServiceOverrideDeleteDialogComponent,
    ApplicationServiceOverrideDeletePopupComponent
  ],
  entryComponents: [
    ApplicationServiceOverrideComponent,
    ApplicationServiceOverrideUpdateComponent,
    ApplicationServiceOverrideDeleteDialogComponent,
    ApplicationServiceOverrideDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminApplicationServiceOverrideModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
