import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceTag } from 'app/shared/model/service-tag.model';

@Component({
  selector: 'jhi-service-tag-detail',
  templateUrl: './service-tag-detail.component.html'
})
export class ServiceTagDetailComponent implements OnInit {
  serviceTag: IServiceTag;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceTag }) => {
      this.serviceTag = serviceTag;
    });
  }

  previousState() {
    window.history.back();
  }
}
