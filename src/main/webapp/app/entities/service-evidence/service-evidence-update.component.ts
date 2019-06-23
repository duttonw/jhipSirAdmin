import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceEvidence, ServiceEvidence } from 'app/shared/model/service-evidence.model';
import { ServiceEvidenceService } from './service-evidence.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-evidence-update',
  templateUrl: './service-evidence-update.component.html'
})
export class ServiceEvidenceUpdateComponent implements OnInit {
  isSaving: boolean;

  categories: ICategory[];

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    evidenceName: [null, [Validators.required, Validators.maxLength(255)]],
    metaData: [null, [Validators.maxLength(255)]],
    migrated: [null, [Validators.maxLength(1)]],
    migratedBy: [null, [Validators.maxLength(10)]],
    displayedForCategoryId: [null, Validators.required],
    serviceRecordId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceEvidenceService: ServiceEvidenceService,
    protected categoryService: CategoryService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceEvidence }) => {
      this.updateForm(serviceEvidence);
    });
    this.categoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategory[]>) => response.body)
      )
      .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceEvidence: IServiceEvidence) {
    this.editForm.patchValue({
      id: serviceEvidence.id,
      createdBy: serviceEvidence.createdBy,
      createdDateTime: serviceEvidence.createdDateTime != null ? serviceEvidence.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceEvidence.modifiedBy,
      modifiedDateTime: serviceEvidence.modifiedDateTime != null ? serviceEvidence.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceEvidence.version,
      evidenceName: serviceEvidence.evidenceName,
      metaData: serviceEvidence.metaData,
      migrated: serviceEvidence.migrated,
      migratedBy: serviceEvidence.migratedBy,
      displayedForCategoryId: serviceEvidence.displayedForCategoryId,
      serviceRecordId: serviceEvidence.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceEvidence = this.createFromForm();
    if (serviceEvidence.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceEvidenceService.update(serviceEvidence));
    } else {
      this.subscribeToSaveResponse(this.serviceEvidenceService.create(serviceEvidence));
    }
  }

  private createFromForm(): IServiceEvidence {
    return {
      ...new ServiceEvidence(),
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
      evidenceName: this.editForm.get(['evidenceName']).value,
      metaData: this.editForm.get(['metaData']).value,
      migrated: this.editForm.get(['migrated']).value,
      migratedBy: this.editForm.get(['migratedBy']).value,
      displayedForCategoryId: this.editForm.get(['displayedForCategoryId']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceEvidence>>) {
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

  trackCategoryById(index: number, item: ICategory) {
    return item.id;
  }

  trackServiceRecordById(index: number, item: IServiceRecord) {
    return item.id;
  }
}
