import { NgModule } from '@angular/core';

import { JhipSirAdminSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [JhipSirAdminSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [JhipSirAdminSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JhipSirAdminSharedCommonModule {}
