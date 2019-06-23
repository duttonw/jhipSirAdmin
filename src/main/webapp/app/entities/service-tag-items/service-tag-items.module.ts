import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceTagItemsComponent,
  ServiceTagItemsDetailComponent,
  ServiceTagItemsUpdateComponent,
  ServiceTagItemsDeletePopupComponent,
  ServiceTagItemsDeleteDialogComponent,
  serviceTagItemsRoute,
  serviceTagItemsPopupRoute
} from './';

const ENTITY_STATES = [...serviceTagItemsRoute, ...serviceTagItemsPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceTagItemsComponent,
    ServiceTagItemsDetailComponent,
    ServiceTagItemsUpdateComponent,
    ServiceTagItemsDeleteDialogComponent,
    ServiceTagItemsDeletePopupComponent
  ],
  entryComponents: [
    ServiceTagItemsComponent,
    ServiceTagItemsUpdateComponent,
    ServiceTagItemsDeleteDialogComponent,
    ServiceTagItemsDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceTagItemsModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
