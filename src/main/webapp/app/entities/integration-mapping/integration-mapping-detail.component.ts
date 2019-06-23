import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIntegrationMapping } from 'app/shared/model/integration-mapping.model';

@Component({
  selector: 'jhi-integration-mapping-detail',
  templateUrl: './integration-mapping-detail.component.html'
})
export class IntegrationMappingDetailComponent implements OnInit {
  integrationMapping: IIntegrationMapping;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ integrationMapping }) => {
      this.integrationMapping = integrationMapping;
    });
  }

  previousState() {
    window.history.back();
  }
}
