import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceName } from 'app/shared/model/service-name.model';

@Component({
  selector: 'jhi-service-name-detail',
  templateUrl: './service-name-detail.component.html'
})
export class ServiceNameDetailComponent implements OnInit {
  serviceName: IServiceName;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceName }) => {
      this.serviceName = serviceName;
    });
  }

  previousState() {
    window.history.back();
  }
}
