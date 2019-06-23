import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IApplicationServiceOverrideTag, ApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';
import { ApplicationServiceOverrideTagService } from './application-service-override-tag.service';

@Component({
  selector: 'jhi-application-service-override-tag-update',
  templateUrl: './application-service-override-tag-update.component.html'
})
export class ApplicationServiceOverrideTagUpdateComponent implements OnInit {
  isSaving: boolean;

  applicationserviceoverridetags: IApplicationServiceOverrideTag[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    migratedBy: [null, [Validators.maxLength(10)]],
    version: [],
    parentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected applicationServiceOverrideTagService: ApplicationServiceOverrideTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideTag }) => {
      this.updateForm(applicationServiceOverrideTag);
    });
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

  updateForm(applicationServiceOverrideTag: IApplicationServiceOverrideTag) {
    this.editForm.patchValue({
      id: applicationServiceOverrideTag.id,
      name: applicationServiceOverrideTag.name,
      createdBy: applicationServiceOverrideTag.createdBy,
      createdDateTime:
        applicationServiceOverrideTag.createdDateTime != null
          ? applicationServiceOverrideTag.createdDateTime.format(DATE_TIME_FORMAT)
          : null,
      modifiedBy: applicationServiceOverrideTag.modifiedBy,
      modifiedDateTime:
        applicationServiceOverrideTag.modifiedDateTime != null
          ? applicationServiceOverrideTag.modifiedDateTime.format(DATE_TIME_FORMAT)
          : null,
      migratedBy: applicationServiceOverrideTag.migratedBy,
      version: applicationServiceOverrideTag.version,
      parentId: applicationServiceOverrideTag.parentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const applicationServiceOverrideTag = this.createFromForm();
    if (applicationServiceOverrideTag.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationServiceOverrideTagService.update(applicationServiceOverrideTag));
    } else {
      this.subscribeToSaveResponse(this.applicationServiceOverrideTagService.create(applicationServiceOverrideTag));
    }
  }

  private createFromForm(): IApplicationServiceOverrideTag {
    return {
      ...new ApplicationServiceOverrideTag(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
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
      parentId: this.editForm.get(['parentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplicationServiceOverrideTag>>) {
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

  trackApplicationServiceOverrideTagById(index: number, item: IApplicationServiceOverrideTag) {
    return item.id;
  }
}
