import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';

@Component({
  selector: 'jhi-service-support-role-context-type-detail',
  templateUrl: './service-support-role-context-type-detail.component.html'
})
export class ServiceSupportRoleContextTypeDetailComponent implements OnInit {
  serviceSupportRoleContextType: IServiceSupportRoleContextType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceSupportRoleContextType }) => {
      this.serviceSupportRoleContextType = serviceSupportRoleContextType;
    });
  }

  previousState() {
    window.history.back();
  }
}
