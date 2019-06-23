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
  IApplicationServiceOverrideTagItems,
  ApplicationServiceOverrideTagItems
} from 'app/shared/model/application-service-override-tag-items.model';
import { ApplicationServiceOverrideTagItemsService } from './application-service-override-tag-items.service';
import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';
import { ApplicationServiceOverrideService } from 'app/entities/application-service-override';
import { IApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';
import { ApplicationServiceOverrideTagService } from 'app/entities/application-service-override-tag';

@Component({
  selector: 'jhi-application-service-override-tag-items-update',
  templateUrl: './application-service-override-tag-items-update.component.html'
})
export class ApplicationServiceOverrideTagItemsUpdateComponent implements OnInit {
  isSaving: boolean;

  applicationserviceoverrides: IApplicationServiceOverride[];

  applicationserviceoverridetags: IApplicationServiceOverrideTag[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    migratedBy: [null, [Validators.maxLength(10)]],
    version: [],
    applicationServiceOverrideId: [null, Validators.required],
    applicationServiceOverrideTagId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected applicationServiceOverrideTagItemsService: ApplicationServiceOverrideTagItemsService,
    protected applicationServiceOverrideService: ApplicationServiceOverrideService,
    protected applicationServiceOverrideTagService: ApplicationServiceOverrideTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideTagItems }) => {
      this.updateForm(applicationServiceOverrideTagItems);
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
    this.applicationServiceOverrideTagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IApplicationServiceOverrideTag[]>) => mayBeOk.ok),
        map((response: HttpResponse<IApplicationServiceOverrideTag[]>) => response.body)
      )
      .subscribe(
        (res: IApplicationServiceOverrideTag[]) => (this.applicationserviceoverridetags = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems) {
    this.editForm.patchValue({
      id: applicationServiceOverrideTagItems.id,
      createdBy: applicationServiceOverrideTagItems.createdBy,
      createdDateTime:
        applicationServiceOverrideTagItems.createdDateTime != null
          ? applicationServiceOverrideTagItems.createdDateTime.format(DATE_TIME_FORMAT)
          : null,
      modifiedBy: applicationServiceOverrideTagItems.modifiedBy,
      modifiedDateTime:
        applicationServiceOverrideTagItems.modifiedDateTime != null
          ? applicationServiceOverrideTagItems.modifiedDateTime.format(DATE_TIME_FORMAT)
          : null,
      migratedBy: applicationServiceOverrideTagItems.migratedBy,
      version: applicationServiceOverrideTagItems.version,
      applicationServiceOverrideId: applicationServiceOverrideTagItems.applicationServiceOverrideId,
      applicationServiceOverrideTagId: applicationServiceOverrideTagItems.applicationServiceOverrideTagId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationServiceOverrideTagItems = this.createFromForm();
    if (applicationServiceOverrideTagItems.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationServiceOverrideTagItemsService.update(applicationServiceOverrideTagItems));
    } else {
      this.subscribeToSaveResponse(this.applicationServiceOverrideTagItemsService.create(applicationServiceOverrideTagItems));
    }
  }

  private createFromForm(): IApplicationServiceOverrideTagItems {
    return {
      ...new ApplicationServiceOverrideTagItems(),
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
      migratedBy: this.editForm.get(['migratedBy']).value,
      version: this.editForm.get(['version']).value,
      applicationServiceOverrideId: this.editForm.get(['applicationServiceOverrideId']).value,
      applicationServiceOverrideTagId: this.editForm.get(['applicationServiceOverrideTagId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationServiceOverrideTagItems>>) {
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

  trackApplicationServiceOverrideTagById(index: number, item: IApplicationServiceOverrideTag) {
    return item.id;
  }
}
