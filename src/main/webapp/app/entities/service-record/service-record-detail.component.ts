import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceRecord } from 'app/shared/model/service-record.model';

@Component({
  selector: 'jhi-service-record-detail',
  templateUrl: './service-record-detail.component.html'
})
export class ServiceRecordDetailComponent implements OnInit {
  serviceRecord: IServiceRecord;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRecord }) => {
      this.serviceRecord = serviceRecord;
    });
  }

  previousState() {
    window.history.back();
  }
}
