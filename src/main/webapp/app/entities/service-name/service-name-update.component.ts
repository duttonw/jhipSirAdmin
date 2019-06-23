import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceName, ServiceName } from 'app/shared/model/service-name.model';
import { ServiceNameService } from './service-name.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-name-update',
  templateUrl: './service-name-update.component.html'
})
export class ServiceNameUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    context: [null, [Validators.maxLength(255)]],
    serviceName: [null, [Validators.maxLength(255)]],
    migrated: [null, [Validators.maxLength(1)]],
    migratedBy: [null, [Validators.maxLength(10)]],
    serviceRecordId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceNameService: ServiceNameService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceName }) => {
      this.updateForm(serviceName);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceName: IServiceName) {
    this.editForm.patchValue({
      id: serviceName.id,
      createdBy: serviceName.createdBy,
      createdDateTime: serviceName.createdDateTime != null ? serviceName.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceName.modifiedBy,
      modifiedDateTime: serviceName.modifiedDateTime != null ? serviceName.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceName.version,
      context: serviceName.context,
      serviceName: serviceName.serviceName,
      migrated: serviceName.migrated,
      migratedBy: serviceName.migratedBy,
      serviceRecordId: serviceName.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceName = this.createFromForm();
    if (serviceName.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceNameService.update(serviceName));
    } else {
      this.subscribeToSaveResponse(this.serviceNameService.create(serviceName));
    }
  }

  private createFromForm(): IServiceName {
    return {
      ...new ServiceName(),
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
      context: this.editForm.get(['context']).value,
      serviceName: this.editForm.get(['serviceName']).value,
      migrated: this.editForm.get(['migrated']).value,
      migratedBy: this.editForm.get(['migratedBy']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceName>>) {
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
