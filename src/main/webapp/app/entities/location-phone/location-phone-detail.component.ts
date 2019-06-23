import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocationPhone } from 'app/shared/model/location-phone.model';

@Component({
  selector: 'jhi-location-phone-detail',
  templateUrl: './location-phone-detail.component.html'
})
export class LocationPhoneDetailComponent implements OnInit {
  locationPhone: ILocationPhone;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationPhone }) => {
      this.locationPhone = locationPhone;
    });
  }

  previousState() {
    window.history.back();
  }
}
