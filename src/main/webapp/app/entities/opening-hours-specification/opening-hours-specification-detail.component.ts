import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';

@Component({
  selector: 'jhi-opening-hours-specification-detail',
  templateUrl: './opening-hours-specification-detail.component.html'
})
export class OpeningHoursSpecificationDetailComponent implements OnInit {
  openingHoursSpecification: IOpeningHoursSpecification;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ openingHoursSpecification }) => {
      this.openingHoursSpecification = openingHoursSpecification;
    });
  }

  previousState() {
    window.history.back();
  }
}
