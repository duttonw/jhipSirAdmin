import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';

@Component({
  selector: 'jhi-application-service-override-attribute-detail',
  templateUrl: './application-service-override-attribute-detail.component.html'
})
export class ApplicationServiceOverrideAttributeDetailComponent implements OnInit {
  applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideAttribute }) => {
      this.applicationServiceOverrideAttribute = applicationServiceOverrideAttribute;
    });
  }

  previousState() {
    window.history.back();
  }
}
