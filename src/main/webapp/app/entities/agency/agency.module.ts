import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  AgencyComponent,
  AgencyDetailComponent,
  AgencyUpdateComponent,
  AgencyDeletePopupComponent,
  AgencyDeleteDialogComponent,
  agencyRoute,
  agencyPopupRoute
} from './';

const ENTITY_STATES = [...agencyRoute, ...agencyPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AgencyComponent, AgencyDetailComponent, AgencyUpdateComponent, AgencyDeleteDialogComponent, AgencyDeletePopupComponent],
  entryComponents: [AgencyComponent, AgencyUpdateComponent, AgencyDeleteDialogComponent, AgencyDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminAgencyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
