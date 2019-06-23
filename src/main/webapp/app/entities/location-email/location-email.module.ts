import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  LocationEmailComponent,
  LocationEmailDetailComponent,
  LocationEmailUpdateComponent,
  LocationEmailDeletePopupComponent,
  LocationEmailDeleteDialogComponent,
  locationEmailRoute,
  locationEmailPopupRoute
} from './';

const ENTITY_STATES = [...locationEmailRoute, ...locationEmailPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LocationEmailComponent,
    LocationEmailDetailComponent,
    LocationEmailUpdateComponent,
    LocationEmailDeleteDialogComponent,
    LocationEmailDeletePopupComponent
  ],
  entryComponents: [
    LocationEmailComponent,
    LocationEmailUpdateComponent,
    LocationEmailDeleteDialogComponent,
    LocationEmailDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminLocationEmailModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
