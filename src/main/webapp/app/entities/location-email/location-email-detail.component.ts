import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocationEmail } from 'app/shared/model/location-email.model';

@Component({
  selector: 'jhi-location-email-detail',
  templateUrl: './location-email-detail.component.html'
})
export class LocationEmailDetailComponent implements OnInit {
  locationEmail: ILocationEmail;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationEmail }) => {
      this.locationEmail = locationEmail;
    });
  }

  previousState() {
    window.history.back();
  }
}
