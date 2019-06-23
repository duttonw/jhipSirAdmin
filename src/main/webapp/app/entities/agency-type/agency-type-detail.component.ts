import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgencyType } from 'app/shared/model/agency-type.model';

@Component({
  selector: 'jhi-agency-type-detail',
  templateUrl: './agency-type-detail.component.html'
})
export class AgencyTypeDetailComponent implements OnInit {
  agencyType: IAgencyType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agencyType }) => {
      this.agencyType = agencyType;
    });
  }

  previousState() {
    window.history.back();
  }
}
