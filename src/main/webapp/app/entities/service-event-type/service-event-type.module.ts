import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceEventTypeComponent,
  ServiceEventTypeDetailComponent,
  ServiceEventTypeUpdateComponent,
  ServiceEventTypeDeletePopupComponent,
  ServiceEventTypeDeleteDialogComponent,
  serviceEventTypeRoute,
  serviceEventTypePopupRoute
} from './';

const ENTITY_STATES = [...serviceEventTypeRoute, ...serviceEventTypePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceEventTypeComponent,
    ServiceEventTypeDetailComponent,
    ServiceEventTypeUpdateComponent,
    ServiceEventTypeDeleteDialogComponent,
    ServiceEventTypeDeletePopupComponent
  ],
  entryComponents: [
    ServiceEventTypeComponent,
    ServiceEventTypeUpdateComponent,
    ServiceEventTypeDeleteDialogComponent,
    ServiceEventTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceEventTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
