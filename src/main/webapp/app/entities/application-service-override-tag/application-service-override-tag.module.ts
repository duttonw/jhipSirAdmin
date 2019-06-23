import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ApplicationServiceOverrideTagComponent,
  ApplicationServiceOverrideTagDetailComponent,
  ApplicationServiceOverrideTagUpdateComponent,
  ApplicationServiceOverrideTagDeletePopupComponent,
  ApplicationServiceOverrideTagDeleteDialogComponent,
  applicationServiceOverrideTagRoute,
  applicationServiceOverrideTagPopupRoute
} from './';

const ENTITY_STATES = [...applicationServiceOverrideTagRoute, ...applicationServiceOverrideTagPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationServiceOverrideTagComponent,
    ApplicationServiceOverrideTagDetailComponent,
    ApplicationServiceOverrideTagUpdateComponent,
    ApplicationServiceOverrideTagDeleteDialogComponent,
    ApplicationServiceOverrideTagDeletePopupComponent
  ],
  entryComponents: [
    ApplicationServiceOverrideTagComponent,
    ApplicationServiceOverrideTagUpdateComponent,
    ApplicationServiceOverrideTagDeleteDialogComponent,
    ApplicationServiceOverrideTagDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminApplicationServiceOverrideTagModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
