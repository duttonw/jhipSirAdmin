import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceDeliveryOrganisationComponent,
  ServiceDeliveryOrganisationDetailComponent,
  ServiceDeliveryOrganisationUpdateComponent,
  ServiceDeliveryOrganisationDeletePopupComponent,
  ServiceDeliveryOrganisationDeleteDialogComponent,
  serviceDeliveryOrganisationRoute,
  serviceDeliveryOrganisationPopupRoute
} from './';

const ENTITY_STATES = [...serviceDeliveryOrganisationRoute, ...serviceDeliveryOrganisationPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceDeliveryOrganisationComponent,
    ServiceDeliveryOrganisationDetailComponent,
    ServiceDeliveryOrganisationUpdateComponent,
    ServiceDeliveryOrganisationDeleteDialogComponent,
    ServiceDeliveryOrganisationDeletePopupComponent
  ],
  entryComponents: [
    ServiceDeliveryOrganisationComponent,
    ServiceDeliveryOrganisationUpdateComponent,
    ServiceDeliveryOrganisationDeleteDialogComponent,
    ServiceDeliveryOrganisationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceDeliveryOrganisationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
