import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceDeliveryComponent,
  ServiceDeliveryDetailComponent,
  ServiceDeliveryUpdateComponent,
  ServiceDeliveryDeletePopupComponent,
  ServiceDeliveryDeleteDialogComponent,
  serviceDeliveryRoute,
  serviceDeliveryPopupRoute
} from './';

const ENTITY_STATES = [...serviceDeliveryRoute, ...serviceDeliveryPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceDeliveryComponent,
    ServiceDeliveryDetailComponent,
    ServiceDeliveryUpdateComponent,
    ServiceDeliveryDeleteDialogComponent,
    ServiceDeliveryDeletePopupComponent
  ],
  entryComponents: [
    ServiceDeliveryComponent,
    ServiceDeliveryUpdateComponent,
    ServiceDeliveryDeleteDialogComponent,
    ServiceDeliveryDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceDeliveryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
