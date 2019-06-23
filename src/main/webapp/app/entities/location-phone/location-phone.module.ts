import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  LocationPhoneComponent,
  LocationPhoneDetailComponent,
  LocationPhoneUpdateComponent,
  LocationPhoneDeletePopupComponent,
  LocationPhoneDeleteDialogComponent,
  locationPhoneRoute,
  locationPhonePopupRoute
} from './';

const ENTITY_STATES = [...locationPhoneRoute, ...locationPhonePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LocationPhoneComponent,
    LocationPhoneDetailComponent,
    LocationPhoneUpdateComponent,
    LocationPhoneDeleteDialogComponent,
    LocationPhoneDeletePopupComponent
  ],
  entryComponents: [
    LocationPhoneComponent,
    LocationPhoneUpdateComponent,
    LocationPhoneDeleteDialogComponent,
    LocationPhoneDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminLocationPhoneModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
