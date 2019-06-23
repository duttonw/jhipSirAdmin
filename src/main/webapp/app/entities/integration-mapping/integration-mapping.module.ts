import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  IntegrationMappingComponent,
  IntegrationMappingDetailComponent,
  IntegrationMappingUpdateComponent,
  IntegrationMappingDeletePopupComponent,
  IntegrationMappingDeleteDialogComponent,
  integrationMappingRoute,
  integrationMappingPopupRoute
} from './';

const ENTITY_STATES = [...integrationMappingRoute, ...integrationMappingPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IntegrationMappingComponent,
    IntegrationMappingDetailComponent,
    IntegrationMappingUpdateComponent,
    IntegrationMappingDeleteDialogComponent,
    IntegrationMappingDeletePopupComponent
  ],
  entryComponents: [
    IntegrationMappingComponent,
    IntegrationMappingUpdateComponent,
    IntegrationMappingDeleteDialogComponent,
    IntegrationMappingDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminIntegrationMappingModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
