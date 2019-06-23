import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  AvailabilityHoursComponent,
  AvailabilityHoursDetailComponent,
  AvailabilityHoursUpdateComponent,
  AvailabilityHoursDeletePopupComponent,
  AvailabilityHoursDeleteDialogComponent,
  availabilityHoursRoute,
  availabilityHoursPopupRoute
} from './';

const ENTITY_STATES = [...availabilityHoursRoute, ...availabilityHoursPopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AvailabilityHoursComponent,
    AvailabilityHoursDetailComponent,
    AvailabilityHoursUpdateComponent,
    AvailabilityHoursDeleteDialogComponent,
    AvailabilityHoursDeletePopupComponent
  ],
  entryComponents: [
    AvailabilityHoursComponent,
    AvailabilityHoursUpdateComponent,
    AvailabilityHoursDeleteDialogComponent,
    AvailabilityHoursDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminAvailabilityHoursModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
