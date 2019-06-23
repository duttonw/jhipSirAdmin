import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  ServiceNameComponent,
  ServiceNameDetailComponent,
  ServiceNameUpdateComponent,
  ServiceNameDeletePopupComponent,
  ServiceNameDeleteDialogComponent,
  serviceNameRoute,
  serviceNamePopupRoute
} from './';

const ENTITY_STATES = [...serviceNameRoute, ...serviceNamePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceNameComponent,
    ServiceNameDetailComponent,
    ServiceNameUpdateComponent,
    ServiceNameDeleteDialogComponent,
    ServiceNameDeletePopupComponent
  ],
  entryComponents: [ServiceNameComponent, ServiceNameUpdateComponent, ServiceNameDeleteDialogComponent, ServiceNameDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminServiceNameModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
