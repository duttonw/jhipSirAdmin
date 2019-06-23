import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  AgencySupportRoleContextTypeComponent,
  AgencySupportRoleContextTypeDetailComponent,
  AgencySupportRoleContextTypeUpdateComponent,
  AgencySupportRoleContextTypeDeletePopupComponent,
  AgencySupportRoleContextTypeDeleteDialogComponent,
  agencySupportRoleContextTypeRoute,
  agencySupportRoleContextTypePopupRoute
} from './';

const ENTITY_STATES = [...agencySupportRoleContextTypeRoute, ...agencySupportRoleContextTypePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgencySupportRoleContextTypeComponent,
    AgencySupportRoleContextTypeDetailComponent,
    AgencySupportRoleContextTypeUpdateComponent,
    AgencySupportRoleContextTypeDeleteDialogComponent,
    AgencySupportRoleContextTypeDeletePopupComponent
  ],
  entryComponents: [
    AgencySupportRoleContextTypeComponent,
    AgencySupportRoleContextTypeUpdateComponent,
    AgencySupportRoleContextTypeDeleteDialogComponent,
    AgencySupportRoleContextTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminAgencySupportRoleContextTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
