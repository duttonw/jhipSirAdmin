import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';

@Component({
  selector: 'jhi-availability-hours-detail',
  templateUrl: './availability-hours-detail.component.html'
})
export class AvailabilityHoursDetailComponent implements OnInit {
  availabilityHours: IAvailabilityHours;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ availabilityHours }) => {
      this.availabilityHours = availabilityHours;
    });
  }

  previousState() {
    window.history.back();
  }
}
