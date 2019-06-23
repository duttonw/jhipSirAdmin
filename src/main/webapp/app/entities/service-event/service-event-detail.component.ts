import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceEvent } from 'app/shared/model/service-event.model';

@Component({
  selector: 'jhi-service-event-detail',
  templateUrl: './service-event-detail.component.html'
})
export class ServiceEventDetailComponent implements OnInit {
  serviceEvent: IServiceEvent;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceEvent }) => {
      this.serviceEvent = serviceEvent;
    });
  }

  previousState() {
    window.history.back();
  }
}
