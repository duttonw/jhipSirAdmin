import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IApplicationServiceOverride, ApplicationServiceOverride } from 'app/shared/model/application-service-override.model';
import { ApplicationServiceOverrideService } from './application-service-override.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';
import { IApplication } from 'app/shared/model/application.model';
import { ApplicationService } from 'app/entities/application';

@Component({
  selector: 'jhi-application-service-override-update',
  templateUrl: './application-service-override-update.component.html'
})
export class ApplicationServiceOverrideUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  applications: IApplication[];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.maxLength(255)]],
    eligibility: [null, [Validators.maxLength(255)]],
    keywords: [null, [Validators.maxLength(255)]],
    longDescription: [null, [Validators.maxLength(255)]],
    name: [null, [Validators.maxLength(255)]],
    preRequisites: [null, [Validators.maxLength(255)]],
    fees: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.maxLength(1)]],
    referenceUrl: [null, [Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    migratedBy: [null, [Validators.maxLength(10)]],
    version: [],
    howTo: [null, [Validators.maxLength(255)]],
    serviceRecordId: [null, Validators.required],
    applicationId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected applicationServiceOverrideService: ApplicationServiceOverrideService,
    protected serviceRecordService: ServiceRecordService,
    protected applicationService: ApplicationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationServiceOverride }) => {
      this.updateForm(applicationServiceOverride);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.applicationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApplication[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApplication[]>) => response.body)
      )
      .subscribe((res: IApplication[]) => (this.applications = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(applicationServiceOverride: IApplicationServiceOverride) {
    this.editForm.patchValue({
      id: applicationServiceOverride.id,
      description: applicationServiceOverride.description,
      eligibility: applicationServiceOverride.eligibility,
      keywords: applicationServiceOverride.keywords,
      longDescription: applicationServiceOverride.longDescription,
      name: applicationServiceOverride.name,
      preRequisites: applicationServiceOverride.preRequisites,
      fees: applicationServiceOverride.fees,
      active: applicationServiceOverride.active,
      referenceUrl: applicationServiceOverride.referenceUrl,
      createdBy: applicationServiceOverride.createdBy,
      createdDateTime:
        applicationServiceOverride.createdDateTime != null ? applicationServiceOverride.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: applicationServiceOverride.modifiedBy,
      modifiedDateTime:
        applicationServiceOverride.modifiedDateTime != null ? applicationServiceOverride.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      migratedBy: applicationServiceOverride.migratedBy,
      version: applicationServiceOverride.version,
      howTo: applicationServiceOverride.howTo,
      serviceRecordId: applicationServiceOverride.serviceRecordId,
      applicationId: applicationServiceOverride.applicationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationServiceOverride = this.createFromForm();
    if (applicationServiceOverride.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationServiceOverrideService.update(applicationServiceOverride));
    } else {
      this.subscribeToSaveResponse(this.applicationServiceOverrideService.create(applicationServiceOverride));
    }
  }

  private createFromForm(): IApplicationServiceOverride {
    return {
      ...new ApplicationServiceOverride(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      eligibility: this.editForm.get(['eligibility']).value,
      keywords: this.editForm.get(['keywords']).value,
      longDescription: this.editForm.get(['longDescription']).value,
      name: this.editForm.get(['name']).value,
      preRequisites: this.editForm.get(['preRequisites']).value,
      fees: this.editForm.get(['fees']).value,
      active: this.editForm.get(['active']).value,
      referenceUrl: this.editForm.get(['referenceUrl']).value,
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
      migratedBy: this.editForm.get(['migratedBy']).value,
      version: this.editForm.get(['version']).value,
      howTo: this.editForm.get(['howTo']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value,
      applicationId: this.editForm.get(['applicationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationServiceOverride>>) {
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

  trackApplicationById(index: number, item: IApplication) {
    return item.id;
  }
}
