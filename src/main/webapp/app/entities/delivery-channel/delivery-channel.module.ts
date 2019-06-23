import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  DeliveryChannelComponent,
  DeliveryChannelDetailComponent,
  DeliveryChannelUpdateComponent,
  DeliveryChannelDeletePopupComponent,
  DeliveryChannelDeleteDialogComponent,
  deliveryChannelRoute,
  deliveryChannelPopupRoute
} from './';

const ENTITY_STATES = [...deliveryChannelRoute, ...deliveryChannelPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DeliveryChannelComponent,
    DeliveryChannelDetailComponent,
    DeliveryChannelUpdateComponent,
    DeliveryChannelDeleteDialogComponent,
    DeliveryChannelDeletePopupComponent
  ],
  entryComponents: [
    DeliveryChannelComponent,
    DeliveryChannelUpdateComponent,
    DeliveryChannelDeleteDialogComponent,
    DeliveryChannelDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminDeliveryChannelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
