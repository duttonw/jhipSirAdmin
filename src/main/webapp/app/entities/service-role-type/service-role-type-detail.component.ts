import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRoleType } from 'app/shared/model/service-role-type.model';

@Component({
  selector: 'jhi-service-role-type-detail',
  templateUrl: './service-role-type-detail.component.html'
})
export class ServiceRoleTypeDetailComponent implements OnInit {
  serviceRoleType: IServiceRoleType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRoleType }) => {
      this.serviceRoleType = serviceRoleType;
    });
  }

  previousState() {
    window.history.back();
  }
}
