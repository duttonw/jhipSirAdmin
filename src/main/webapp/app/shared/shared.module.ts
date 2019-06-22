import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { JhipSirAdminSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [JhipSirAdminSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JhipSirAdminSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipSirAdminSharedModule {
  static forRoot() {
    return {
      ngModule: JhipSirAdminSharedModule
    };
  }
}
