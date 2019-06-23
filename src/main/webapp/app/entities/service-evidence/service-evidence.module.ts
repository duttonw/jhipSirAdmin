import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceEvidenceComponent,
  ServiceEvidenceDetailComponent,
  ServiceEvidenceUpdateComponent,
  ServiceEvidenceDeletePopupComponent,
  ServiceEvidenceDeleteDialogComponent,
  serviceEvidenceRoute,
  serviceEvidencePopupRoute
} from './';

const ENTITY_STATES = [...serviceEvidenceRoute, ...serviceEvidencePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceEvidenceComponent,
    ServiceEvidenceDetailComponent,
    ServiceEvidenceUpdateComponent,
    ServiceEvidenceDeleteDialogComponent,
    ServiceEvidenceDeletePopupComponent
  ],
  entryComponents: [
    ServiceEvidenceComponent,
    ServiceEvidenceUpdateComponent,
    ServiceEvidenceDeleteDialogComponent,
    ServiceEvidenceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceEvidenceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
