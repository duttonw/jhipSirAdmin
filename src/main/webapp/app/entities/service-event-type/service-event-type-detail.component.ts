import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceEventType } from 'app/shared/model/service-event-type.model';

@Component({
  selector: 'jhi-service-event-type-detail',
  templateUrl: './service-event-type-detail.component.html'
})
export class ServiceEventTypeDetailComponent implements OnInit {
  serviceEventType: IServiceEventType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceEventType }) => {
      this.serviceEventType = serviceEventType;
    });
  }

  previousState() {
    window.history.back();
  }
}
