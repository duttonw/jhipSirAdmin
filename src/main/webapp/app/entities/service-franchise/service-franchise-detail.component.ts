import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceFranchise } from 'app/shared/model/service-franchise.model';

@Component({
  selector: 'jhi-service-franchise-detail',
  templateUrl: './service-franchise-detail.component.html'
})
export class ServiceFranchiseDetailComponent implements OnInit {
  serviceFranchise: IServiceFranchise;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceFranchise }) => {
      this.serviceFranchise = serviceFranchise;
    });
  }

  previousState() {
    window.history.back();
  }
}
