import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  LocationAddressComponent,
  LocationAddressDetailComponent,
  LocationAddressUpdateComponent,
  LocationAddressDeletePopupComponent,
  LocationAddressDeleteDialogComponent,
  locationAddressRoute,
  locationAddressPopupRoute
} from './';

const ENTITY_STATES = [...locationAddressRoute, ...locationAddressPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LocationAddressComponent,
    LocationAddressDetailComponent,
    LocationAddressUpdateComponent,
    LocationAddressDeleteDialogComponent,
    LocationAddressDeletePopupComponent
  ],
  entryComponents: [
    LocationAddressComponent,
    LocationAddressUpdateComponent,
    LocationAddressDeleteDialogComponent,
    LocationAddressDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminLocationAddressModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
