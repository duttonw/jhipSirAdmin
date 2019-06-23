import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ApplicationServiceOverrideTagItemsComponent,
  ApplicationServiceOverrideTagItemsDetailComponent,
  ApplicationServiceOverrideTagItemsUpdateComponent,
  ApplicationServiceOverrideTagItemsDeletePopupComponent,
  ApplicationServiceOverrideTagItemsDeleteDialogComponent,
  applicationServiceOverrideTagItemsRoute,
  applicationServiceOverrideTagItemsPopupRoute
} from './';

const ENTITY_STATES = [...applicationServiceOverrideTagItemsRoute, ...applicationServiceOverrideTagItemsPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ApplicationServiceOverrideTagItemsComponent,
    ApplicationServiceOverrideTagItemsDetailComponent,
    ApplicationServiceOverrideTagItemsUpdateComponent,
    ApplicationServiceOverrideTagItemsDeleteDialogComponent,
    ApplicationServiceOverrideTagItemsDeletePopupComponent
  ],
  entryComponents: [
    ApplicationServiceOverrideTagItemsComponent,
    ApplicationServiceOverrideTagItemsUpdateComponent,
    ApplicationServiceOverrideTagItemsDeleteDialogComponent,
    ApplicationServiceOverrideTagItemsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminApplicationServiceOverrideTagItemsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
