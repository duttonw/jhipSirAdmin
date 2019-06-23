import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRelationship } from 'app/shared/model/service-relationship.model';

@Component({
  selector: 'jhi-service-relationship-detail',
  templateUrl: './service-relationship-detail.component.html'
})
export class ServiceRelationshipDetailComponent implements OnInit {
  serviceRelationship: IServiceRelationship;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRelationship }) => {
      this.serviceRelationship = serviceRelationship;
    });
  }

  previousState() {
    window.history.back();
  }
}
