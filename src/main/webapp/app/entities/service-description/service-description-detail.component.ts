import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceDescription } from 'app/shared/model/service-description.model';

@Component({
  selector: 'jhi-service-description-detail',
  templateUrl: './service-description-detail.component.html'
})
export class ServiceDescriptionDetailComponent implements OnInit {
  serviceDescription: IServiceDescription;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDescription }) => {
      this.serviceDescription = serviceDescription;
    });
  }

  previousState() {
    window.history.back();
  }
}
