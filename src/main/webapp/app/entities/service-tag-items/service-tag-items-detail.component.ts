import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceTagItems } from 'app/shared/model/service-tag-items.model';

@Component({
  selector: 'jhi-service-tag-items-detail',
  templateUrl: './service-tag-items-detail.component.html'
})
export class ServiceTagItemsDetailComponent implements OnInit {
  serviceTagItems: IServiceTagItems;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceTagItems }) => {
      this.serviceTagItems = serviceTagItems;
    });
  }

  previousState() {
    window.history.back();
  }
}
