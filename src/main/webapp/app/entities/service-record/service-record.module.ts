import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceRecordComponent,
  ServiceRecordDetailComponent,
  ServiceRecordUpdateComponent,
  ServiceRecordDeletePopupComponent,
  ServiceRecordDeleteDialogComponent,
  serviceRecordRoute,
  serviceRecordPopupRoute
} from './';

const ENTITY_STATES = [...serviceRecordRoute, ...serviceRecordPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceRecordComponent,
    ServiceRecordDetailComponent,
    ServiceRecordUpdateComponent,
    ServiceRecordDeleteDialogComponent,
    ServiceRecordDeletePopupComponent
  ],
  entryComponents: [
    ServiceRecordComponent,
    ServiceRecordUpdateComponent,
    ServiceRecordDeleteDialogComponent,
    ServiceRecordDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceRecordModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
