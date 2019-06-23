import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceFranchiseComponent,
  ServiceFranchiseDetailComponent,
  ServiceFranchiseUpdateComponent,
  ServiceFranchiseDeletePopupComponent,
  ServiceFranchiseDeleteDialogComponent,
  serviceFranchiseRoute,
  serviceFranchisePopupRoute
} from './';

const ENTITY_STATES = [...serviceFranchiseRoute, ...serviceFranchisePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceFranchiseComponent,
    ServiceFranchiseDetailComponent,
    ServiceFranchiseUpdateComponent,
    ServiceFranchiseDeleteDialogComponent,
    ServiceFranchiseDeletePopupComponent
  ],
  entryComponents: [
    ServiceFranchiseComponent,
    ServiceFranchiseUpdateComponent,
    ServiceFranchiseDeleteDialogComponent,
    ServiceFranchiseDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceFranchiseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
