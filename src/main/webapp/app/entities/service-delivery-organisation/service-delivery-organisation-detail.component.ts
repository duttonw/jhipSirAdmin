import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';

@Component({
  selector: 'jhi-service-delivery-organisation-detail',
  templateUrl: './service-delivery-organisation-detail.component.html'
})
export class ServiceDeliveryOrganisationDetailComponent implements OnInit {
  serviceDeliveryOrganisation: IServiceDeliveryOrganisation;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDeliveryOrganisation }) => {
      this.serviceDeliveryOrganisation = serviceDeliveryOrganisation;
    });
  }

  previousState() {
    window.history.back();
  }
}
