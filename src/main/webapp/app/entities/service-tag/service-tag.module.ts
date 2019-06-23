import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceTagComponent,
  ServiceTagDetailComponent,
  ServiceTagUpdateComponent,
  ServiceTagDeletePopupComponent,
  ServiceTagDeleteDialogComponent,
  serviceTagRoute,
  serviceTagPopupRoute
} from './';

const ENTITY_STATES = [...serviceTagRoute, ...serviceTagPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceTagComponent,
    ServiceTagDetailComponent,
    ServiceTagUpdateComponent,
    ServiceTagDeleteDialogComponent,
    ServiceTagDeletePopupComponent
  ],
  entryComponents: [ServiceTagComponent, ServiceTagUpdateComponent, ServiceTagDeleteDialogComponent, ServiceTagDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceTagModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
