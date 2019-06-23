import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceDescription, ServiceDescription } from 'app/shared/model/service-description.model';
import { ServiceDescriptionService } from './service-description.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-description-update',
  templateUrl: './service-description-update.component.html'
})
export class ServiceDescriptionUpdateComponent implements OnInit {
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
    serviceDescription: [null, [Validators.maxLength(255)]],
    migrated: [null, [Validators.maxLength(1)]],
    migratedBy: [null, [Validators.maxLength(10)]],
    serviceRecordId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceDescriptionService: ServiceDescriptionService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceDescription }) => {
      this.updateForm(serviceDescription);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceDescription: IServiceDescription) {
    this.editForm.patchValue({
      id: serviceDescription.id,
      createdBy: serviceDescription.createdBy,
      createdDateTime: serviceDescription.createdDateTime != null ? serviceDescription.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceDescription.modifiedBy,
      modifiedDateTime: serviceDescription.modifiedDateTime != null ? serviceDescription.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceDescription.version,
      context: serviceDescription.context,
      serviceDescription: serviceDescription.serviceDescription,
      migrated: serviceDescription.migrated,
      migratedBy: serviceDescription.migratedBy,
      serviceRecordId: serviceDescription.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceDescription = this.createFromForm();
    if (serviceDescription.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceDescriptionService.update(serviceDescription));
    } else {
      this.subscribeToSaveResponse(this.serviceDescriptionService.create(serviceDescription));
    }
  }

  private createFromForm(): IServiceDescription {
    return {
      ...new ServiceDescription(),
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
      serviceDescription: this.editForm.get(['serviceDescription']).value,
      migrated: this.editForm.get(['migrated']).value,
      migratedBy: this.editForm.get(['migratedBy']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceDescription>>) {
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
