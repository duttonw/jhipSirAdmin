import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ApplicationServiceOverrideAttributeComponent,
  ApplicationServiceOverrideAttributeDetailComponent,
  ApplicationServiceOverrideAttributeUpdateComponent,
  ApplicationServiceOverrideAttributeDeletePopupComponent,
  ApplicationServiceOverrideAttributeDeleteDialogComponent,
  applicationServiceOverrideAttributeRoute,
  applicationServiceOverrideAttributePopupRoute
} from './';

const ENTITY_STATES = [...applicationServiceOverrideAttributeRoute, ...applicationServiceOverrideAttributePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationServiceOverrideAttributeComponent,
    ApplicationServiceOverrideAttributeDetailComponent,
    ApplicationServiceOverrideAttributeUpdateComponent,
    ApplicationServiceOverrideAttributeDeleteDialogComponent,
    ApplicationServiceOverrideAttributeDeletePopupComponent
  ],
  entryComponents: [
    ApplicationServiceOverrideAttributeComponent,
    ApplicationServiceOverrideAttributeUpdateComponent,
    ApplicationServiceOverrideAttributeDeleteDialogComponent,
    ApplicationServiceOverrideAttributeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminApplicationServiceOverrideAttributeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
