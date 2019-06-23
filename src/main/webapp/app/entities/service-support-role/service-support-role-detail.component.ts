import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';

@Component({
  selector: 'jhi-service-support-role-detail',
  templateUrl: './service-support-role-detail.component.html'
})
export class ServiceSupportRoleDetailComponent implements OnInit {
  serviceSupportRole: IServiceSupportRole;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceSupportRole }) => {
      this.serviceSupportRole = serviceSupportRole;
    });
  }

  previousState() {
    window.history.back();
  }
}
