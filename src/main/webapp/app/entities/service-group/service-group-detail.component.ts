import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceGroup } from 'app/shared/model/service-group.model';

@Component({
  selector: 'jhi-service-group-detail',
  templateUrl: './service-group-detail.component.html'
})
export class ServiceGroupDetailComponent implements OnInit {
  serviceGroup: IServiceGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceGroup }) => {
      this.serviceGroup = serviceGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}
