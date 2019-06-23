import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { JhipSirAdminSharedModule } from 'app/shared';
import {
  AgencySupportRoleComponent,
  AgencySupportRoleDetailComponent,
  AgencySupportRoleUpdateComponent,
  AgencySupportRoleDeletePopupComponent,
  AgencySupportRoleDeleteDialogComponent,
  agencySupportRoleRoute,
  agencySupportRolePopupRoute
} from './';

const ENTITY_STATES = [...agencySupportRoleRoute, ...agencySupportRolePopupRoute];

@NgModule({
  imports: [JhipSirAdminSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AgencySupportRoleComponent,
    AgencySupportRoleDetailComponent,
    AgencySupportRoleUpdateComponent,
    AgencySupportRoleDeleteDialogComponent,
    AgencySupportRoleDeletePopupComponent
  ],
  entryComponents: [
    AgencySupportRoleComponent,
    AgencySupportRoleUpdateComponent,
    AgencySupportRoleDeleteDialogComponent,
    AgencySupportRoleDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminAgencySupportRoleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
