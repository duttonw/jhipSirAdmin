import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceEvidence } from 'app/shared/model/service-evidence.model';

@Component({
  selector: 'jhi-service-evidence-detail',
  templateUrl: './service-evidence-detail.component.html'
})
export class ServiceEvidenceDetailComponent implements OnInit {
  serviceEvidence: IServiceEvidence;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceEvidence }) => {
      this.serviceEvidence = serviceEvidence;
    });
  }

  previousState() {
    window.history.back();
  }
}
