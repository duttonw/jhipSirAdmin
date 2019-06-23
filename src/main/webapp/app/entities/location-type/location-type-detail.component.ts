import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocationType } from 'app/shared/model/location-type.model';

@Component({
  selector: 'jhi-location-type-detail',
  templateUrl: './location-type-detail.component.html'
})
export class LocationTypeDetailComponent implements OnInit {
  locationType: ILocationType;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationType }) => {
      this.locationType = locationType;
    });
  }

  previousState() {
    window.history.back();
  }
}
