import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceDeliveryFormComponent,
  ServiceDeliveryFormDetailComponent,
  ServiceDeliveryFormUpdateComponent,
  ServiceDeliveryFormDeletePopupComponent,
  ServiceDeliveryFormDeleteDialogComponent,
  serviceDeliveryFormRoute,
  serviceDeliveryFormPopupRoute
} from './';

const ENTITY_STATES = [...serviceDeliveryFormRoute, ...serviceDeliveryFormPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceDeliveryFormComponent,
    ServiceDeliveryFormDetailComponent,
    ServiceDeliveryFormUpdateComponent,
    ServiceDeliveryFormDeleteDialogComponent,
    ServiceDeliveryFormDeletePopupComponent
  ],
  entryComponents: [
    ServiceDeliveryFormComponent,
    ServiceDeliveryFormUpdateComponent,
    ServiceDeliveryFormDeleteDialogComponent,
    ServiceDeliveryFormDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceDeliveryFormModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
