import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';

@Component({
  selector: 'jhi-service-delivery-form-detail',
  templateUrl: './service-delivery-form-detail.component.html'
})
export class ServiceDeliveryFormDetailComponent implements OnInit {
  serviceDeliveryForm: IServiceDeliveryForm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDeliveryForm }) => {
      this.serviceDeliveryForm = serviceDeliveryForm;
    });
  }

  previousState() {
    window.history.back();
  }
}
