import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceDeliveryForm, ServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';
import { ServiceDeliveryFormService } from './service-delivery-form.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-delivery-form-update',
  templateUrl: './service-delivery-form-update.component.html'
})
export class ServiceDeliveryFormUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    formName: [null, [Validators.maxLength(255)]],
    formUrl: [null, [Validators.maxLength(255)]],
    source: [null, [Validators.maxLength(255)]],
    serviceRecordId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceDeliveryFormService: ServiceDeliveryFormService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceDeliveryForm }) => {
      this.updateForm(serviceDeliveryForm);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceDeliveryForm: IServiceDeliveryForm) {
    this.editForm.patchValue({
      id: serviceDeliveryForm.id,
      createdBy: serviceDeliveryForm.createdBy,
      createdDateTime: serviceDeliveryForm.createdDateTime != null ? serviceDeliveryForm.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceDeliveryForm.modifiedBy,
      modifiedDateTime: serviceDeliveryForm.modifiedDateTime != null ? serviceDeliveryForm.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceDeliveryForm.version,
      formName: serviceDeliveryForm.formName,
      formUrl: serviceDeliveryForm.formUrl,
      source: serviceDeliveryForm.source,
      serviceRecordId: serviceDeliveryForm.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceDeliveryForm = this.createFromForm();
    if (serviceDeliveryForm.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceDeliveryFormService.update(serviceDeliveryForm));
    } else {
      this.subscribeToSaveResponse(this.serviceDeliveryFormService.create(serviceDeliveryForm));
    }
  }

  private createFromForm(): IServiceDeliveryForm {
    return {
      ...new ServiceDeliveryForm(),
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
      formName: this.editForm.get(['formName']).value,
      formUrl: this.editForm.get(['formUrl']).value,
      source: this.editForm.get(['source']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceDeliveryForm>>) {
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
