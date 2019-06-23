import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  AgencyTypeComponent,
  AgencyTypeDetailComponent,
  AgencyTypeUpdateComponent,
  AgencyTypeDeletePopupComponent,
  AgencyTypeDeleteDialogComponent,
  agencyTypeRoute,
  agencyTypePopupRoute
} from './';

const ENTITY_STATES = [...agencyTypeRoute, ...agencyTypePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgencyTypeComponent,
    AgencyTypeDetailComponent,
    AgencyTypeUpdateComponent,
    AgencyTypeDeleteDialogComponent,
    AgencyTypeDeletePopupComponent
  ],
  entryComponents: [AgencyTypeComponent, AgencyTypeUpdateComponent, AgencyTypeDeleteDialogComponent, AgencyTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminAgencyTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
