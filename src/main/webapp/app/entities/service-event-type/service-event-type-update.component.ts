import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IServiceEventType, ServiceEventType } from 'app/shared/model/service-event-type.model';
import { ServiceEventTypeService } from './service-event-type.service';

@Component({
  selector: 'jhi-service-event-type-update',
  templateUrl: './service-event-type-update.component.html'
})
export class ServiceEventTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    serviceEvent: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(
    protected serviceEventTypeService: ServiceEventTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceEventType }) => {
      this.updateForm(serviceEventType);
    });
  }

  updateForm(serviceEventType: IServiceEventType) {
    this.editForm.patchValue({
      id: serviceEventType.id,
      createdBy: serviceEventType.createdBy,
      createdDateTime: serviceEventType.createdDateTime != null ? serviceEventType.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceEventType.modifiedBy,
      modifiedDateTime: serviceEventType.modifiedDateTime != null ? serviceEventType.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceEventType.version,
      serviceEvent: serviceEventType.serviceEvent
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceEventType = this.createFromForm();
    if (serviceEventType.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceEventTypeService.update(serviceEventType));
    } else {
      this.subscribeToSaveResponse(this.serviceEventTypeService.create(serviceEventType));
    }
  }

  private createFromForm(): IServiceEventType {
    return {
      ...new ServiceEventType(),
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
      serviceEvent: this.editForm.get(['serviceEvent']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceEventType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
