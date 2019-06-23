import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

@Component({
  selector: 'jhi-application-service-override-tag-items-detail',
  templateUrl: './application-service-override-tag-items-detail.component.html'
})
export class ApplicationServiceOverrideTagItemsDetailComponent implements OnInit {
  applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideTagItems }) => {
      this.applicationServiceOverrideTagItems = applicationServiceOverrideTagItems;
    });
  }

  previousState() {
    window.history.back();
  }
}
