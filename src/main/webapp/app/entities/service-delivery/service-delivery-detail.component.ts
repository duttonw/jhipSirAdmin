import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceDelivery } from 'app/shared/model/service-delivery.model';

@Component({
  selector: 'jhi-service-delivery-detail',
  templateUrl: './service-delivery-detail.component.html'
})
export class ServiceDeliveryDetailComponent implements OnInit {
  serviceDelivery: IServiceDelivery;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDelivery }) => {
      this.serviceDelivery = serviceDelivery;
    });
  }

  previousState() {
    window.history.back();
  }
}
