import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import {
  IApplicationServiceOverrideAttribute,
  ApplicationServiceOverrideAttribute
} from 'app/shared/model/application-service-override-attribute.model';
import { ApplicationServiceOverrideAttributeService } from './application-service-override-attribute.service';
import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';
import { ApplicationServiceOverrideService } from 'app/entities/application-service-override';

@Component({
  selector: 'jhi-application-service-override-attribute-update',
  templateUrl: './application-service-override-attribute-update.component.html'
})
export class ApplicationServiceOverrideAttributeUpdateComponent implements OnInit {
  isSaving: boolean;

  applicationserviceoverrides: IApplicationServiceOverride[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    value: [null, [Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    migratedBy: [null, [Validators.maxLength(10)]],
    version: [],
    applicationServiceOverrideId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected applicationServiceOverrideAttributeService: ApplicationServiceOverrideAttributeService,
    protected applicationServiceOverrideService: ApplicationServiceOverrideService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideAttribute }) => {
      this.updateForm(applicationServiceOverrideAttribute);
    });
    this.applicationServiceOverrideService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApplicationServiceOverride[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApplicationServiceOverride[]>) => response.body)
      )
      .subscribe(
        (res: IApplicationServiceOverride[]) => (this.applicationserviceoverrides = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute) {
    this.editForm.patchValue({
      id: applicationServiceOverrideAttribute.id,
      name: applicationServiceOverrideAttribute.name,
      value: applicationServiceOverrideAttribute.value,
      createdBy: applicationServiceOverrideAttribute.createdBy,
      createdDateTime:
        applicationServiceOverrideAttribute.createdDateTime != null
          ? applicationServiceOverrideAttribute.createdDateTime.format(DATE_TIME_FORMAT)
          : null,
      modifiedBy: applicationServiceOverrideAttribute.modifiedBy,
      modifiedDateTime:
        applicationServiceOverrideAttribute.modifiedDateTime != null
          ? applicationServiceOverrideAttribute.modifiedDateTime.format(DATE_TIME_FORMAT)
          : null,
      migratedBy: applicationServiceOverrideAttribute.migratedBy,
      version: applicationServiceOverrideAttribute.version,
      applicationServiceOverrideId: applicationServiceOverrideAttribute.applicationServiceOverrideId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationServiceOverrideAttribute = this.createFromForm();
    if (applicationServiceOverrideAttribute.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationServiceOverrideAttributeService.update(applicationServiceOverrideAttribute));
    } else {
      this.subscribeToSaveResponse(this.applicationServiceOverrideAttributeService.create(applicationServiceOverrideAttribute));
    }
  }

  private createFromForm(): IApplicationServiceOverrideAttribute {
    return {
      ...new ApplicationServiceOverrideAttribute(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      value: this.editForm.get(['value']).value,
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
      applicationServiceOverrideId: this.editForm.get(['applicationServiceOverrideId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationServiceOverrideAttribute>>) {
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

  trackApplicationServiceOverrideById(index: number, item: IApplicationServiceOverride) {
    return item.id;
  }
}
