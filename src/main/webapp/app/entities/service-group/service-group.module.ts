import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceGroupComponent,
  ServiceGroupDetailComponent,
  ServiceGroupUpdateComponent,
  ServiceGroupDeletePopupComponent,
  ServiceGroupDeleteDialogComponent,
  serviceGroupRoute,
  serviceGroupPopupRoute
} from './';

const ENTITY_STATES = [...serviceGroupRoute, ...serviceGroupPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceGroupComponent,
    ServiceGroupDetailComponent,
    ServiceGroupUpdateComponent,
    ServiceGroupDeleteDialogComponent,
    ServiceGroupDeletePopupComponent
  ],
  entryComponents: [
    ServiceGroupComponent,
    ServiceGroupUpdateComponent,
    ServiceGroupDeleteDialogComponent,
    ServiceGroupDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceGroupModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
