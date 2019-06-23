import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocationAddress } from 'app/shared/model/location-address.model';

@Component({
  selector: 'jhi-location-address-detail',
  templateUrl: './location-address-detail.component.html'
})
export class LocationAddressDetailComponent implements OnInit {
  locationAddress: ILocationAddress;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationAddress }) => {
      this.locationAddress = locationAddress;
    });
  }

  previousState() {
    window.history.back();
  }
}
