import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  LocationTypeComponent,
  LocationTypeDetailComponent,
  LocationTypeUpdateComponent,
  LocationTypeDeletePopupComponent,
  LocationTypeDeleteDialogComponent,
  locationTypeRoute,
  locationTypePopupRoute
} from './';

const ENTITY_STATES = [...locationTypeRoute, ...locationTypePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LocationTypeComponent,
    LocationTypeDetailComponent,
    LocationTypeUpdateComponent,
    LocationTypeDeleteDialogComponent,
    LocationTypeDeletePopupComponent
  ],
  entryComponents: [
    LocationTypeComponent,
    LocationTypeUpdateComponent,
    LocationTypeDeleteDialogComponent,
    LocationTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminLocationTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
