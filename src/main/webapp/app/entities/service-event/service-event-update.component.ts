import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceEvent, ServiceEvent } from 'app/shared/model/service-event.model';
import { ServiceEventService } from './service-event.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';
import { IServiceEventType } from 'app/shared/model/service-event-type.model';
import { ServiceEventTypeService } from 'app/entities/service-event-type';

@Component({
  selector: 'jhi-service-event-update',
  templateUrl: './service-event-update.component.html'
})
export class ServiceEventUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  serviceeventtypes: IServiceEventType[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    serviceEventSequence: [],
    serviceRecordId: [],
    serviceEventTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceEventService: ServiceEventService,
    protected serviceRecordService: ServiceRecordService,
    protected serviceEventTypeService: ServiceEventTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceEvent }) => {
      this.updateForm(serviceEvent);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceEventTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceEventType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceEventType[]>) => response.body)
      )
      .subscribe((res: IServiceEventType[]) => (this.serviceeventtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceEvent: IServiceEvent) {
    this.editForm.patchValue({
      id: serviceEvent.id,
      createdBy: serviceEvent.createdBy,
      createdDateTime: serviceEvent.createdDateTime != null ? serviceEvent.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceEvent.modifiedBy,
      modifiedDateTime: serviceEvent.modifiedDateTime != null ? serviceEvent.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceEvent.version,
      serviceEventSequence: serviceEvent.serviceEventSequence,
      serviceRecordId: serviceEvent.serviceRecordId,
      serviceEventTypeId: serviceEvent.serviceEventTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceEvent = this.createFromForm();
    if (serviceEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceEventService.update(serviceEvent));
    } else {
      this.subscribeToSaveResponse(this.serviceEventService.create(serviceEvent));
    }
  }

  private createFromForm(): IServiceEvent {
    return {
      ...new ServiceEvent(),
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
      serviceEventSequence: this.editForm.get(['serviceEventSequence']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value,
      serviceEventTypeId: this.editForm.get(['serviceEventTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceEvent>>) {
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

  trackServiceEventTypeById(index: number, item: IServiceEventType) {
    return item.id;
  }
}
