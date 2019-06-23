import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceEventComponent,
  ServiceEventDetailComponent,
  ServiceEventUpdateComponent,
  ServiceEventDeletePopupComponent,
  ServiceEventDeleteDialogComponent,
  serviceEventRoute,
  serviceEventPopupRoute
} from './';

const ENTITY_STATES = [...serviceEventRoute, ...serviceEventPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceEventComponent,
    ServiceEventDetailComponent,
    ServiceEventUpdateComponent,
    ServiceEventDeleteDialogComponent,
    ServiceEventDeletePopupComponent
  ],
  entryComponents: [
    ServiceEventComponent,
    ServiceEventUpdateComponent,
    ServiceEventDeleteDialogComponent,
    ServiceEventDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceEventModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
