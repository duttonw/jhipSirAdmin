import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceRelationshipComponent,
  ServiceRelationshipDetailComponent,
  ServiceRelationshipUpdateComponent,
  ServiceRelationshipDeletePopupComponent,
  ServiceRelationshipDeleteDialogComponent,
  serviceRelationshipRoute,
  serviceRelationshipPopupRoute
} from './';

const ENTITY_STATES = [...serviceRelationshipRoute, ...serviceRelationshipPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceRelationshipComponent,
    ServiceRelationshipDetailComponent,
    ServiceRelationshipUpdateComponent,
    ServiceRelationshipDeleteDialogComponent,
    ServiceRelationshipDeletePopupComponent
  ],
  entryComponents: [
    ServiceRelationshipComponent,
    ServiceRelationshipUpdateComponent,
    ServiceRelationshipDeleteDialogComponent,
    ServiceRelationshipDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceRelationshipModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
