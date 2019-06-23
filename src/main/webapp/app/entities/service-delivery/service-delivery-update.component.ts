import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceDelivery, ServiceDelivery } from 'app/shared/model/service-delivery.model';
import { ServiceDeliveryService } from './service-delivery.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-delivery-update',
  templateUrl: './service-delivery-update.component.html'
})
export class ServiceDeliveryUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    serviceDeliveryChannelType: [null, [Validators.required, Validators.maxLength(255)]],
    status: [null, [Validators.maxLength(255)]],
    serviceRecordId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceDeliveryService: ServiceDeliveryService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceDelivery }) => {
      this.updateForm(serviceDelivery);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceDelivery: IServiceDelivery) {
    this.editForm.patchValue({
      id: serviceDelivery.id,
      createdBy: serviceDelivery.createdBy,
      createdDateTime: serviceDelivery.createdDateTime != null ? serviceDelivery.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceDelivery.modifiedBy,
      modifiedDateTime: serviceDelivery.modifiedDateTime != null ? serviceDelivery.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceDelivery.version,
      serviceDeliveryChannelType: serviceDelivery.serviceDeliveryChannelType,
      status: serviceDelivery.status,
      serviceRecordId: serviceDelivery.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceDelivery = this.createFromForm();
    if (serviceDelivery.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceDeliveryService.update(serviceDelivery));
    } else {
      this.subscribeToSaveResponse(this.serviceDeliveryService.create(serviceDelivery));
    }
  }

  private createFromForm(): IServiceDelivery {
    return {
      ...new ServiceDelivery(),
      id: this.editForm.get(['id']).value,
      createdBy: this.editForm.get(['createdBy']).value,
      createdDateTime:
        this.editForm.get(['createdDateTime']).value != null
          ? moment(this.editForm.get(['createdDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      modifiedBy: this.editForm.get(['modifiedBy']).value,
      modifiedDateTime:
        this.editForm.get(['modifiedDateTime']).value != null
          ? moment(this.editForm.get(['modifiedDateTime']).value, DATE_TIME_FORMAT)
          : undefined,
      version: this.editForm.get(['version']).value,
      serviceDeliveryChannelType: this.editForm.get(['serviceDeliveryChannelType']).value,
      status: this.editForm.get(['status']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceDelivery>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackServiceRecordById(index: number, item: IServiceRecord) {
    return item.id;
  }
}
