import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';

@Component({
  selector: 'jhi-application-service-override-tag-detail',
  templateUrl: './application-service-override-tag-detail.component.html'
})
export class ApplicationServiceOverrideTagDetailComponent implements OnInit {
  applicationServiceOverrideTag: IApplicationServiceOverrideTag;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideTag }) => {
      this.applicationServiceOverrideTag = applicationServiceOverrideTag;
    });
  }

  previousState() {
    window.history.back();
  }
}
