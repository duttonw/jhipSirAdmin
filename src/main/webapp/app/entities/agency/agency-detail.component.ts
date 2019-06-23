import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgency } from 'app/shared/model/agency.model';

@Component({
  selector: 'jhi-agency-detail',
  templateUrl: './agency-detail.component.html'
})
export class AgencyDetailComponent implements OnInit {
  agency: IAgency;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agency }) => {
      this.agency = agency;
    });
  }

  previousState() {
    window.history.back();
  }
}
