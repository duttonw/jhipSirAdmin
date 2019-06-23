import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';

@Component({
  selector: 'jhi-application-service-override-detail',
  templateUrl: './application-service-override-detail.component.html'
})
export class ApplicationServiceOverrideDetailComponent implements OnInit {
  applicationServiceOverride: IApplicationServiceOverride;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverride }) => {
      this.applicationServiceOverride = applicationServiceOverride;
    });
  }

  previousState() {
    window.history.back();
  }
}
