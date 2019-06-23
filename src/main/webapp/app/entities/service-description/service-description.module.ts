import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceDescriptionComponent,
  ServiceDescriptionDetailComponent,
  ServiceDescriptionUpdateComponent,
  ServiceDescriptionDeletePopupComponent,
  ServiceDescriptionDeleteDialogComponent,
  serviceDescriptionRoute,
  serviceDescriptionPopupRoute
} from './';

const ENTITY_STATES = [...serviceDescriptionRoute, ...serviceDescriptionPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceDescriptionComponent,
    ServiceDescriptionDetailComponent,
    ServiceDescriptionUpdateComponent,
    ServiceDescriptionDeleteDialogComponent,
    ServiceDescriptionDeletePopupComponent
  ],
  entryComponents: [
    ServiceDescriptionComponent,
    ServiceDescriptionUpdateComponent,
    ServiceDescriptionDeleteDialogComponent,
    ServiceDescriptionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceDescriptionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
