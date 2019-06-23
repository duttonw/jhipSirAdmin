import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceTag, ServiceTag } from 'app/shared/model/service-tag.model';
import { ServiceTagService } from './service-tag.service';

@Component({
  selector: 'jhi-service-tag-update',
  templateUrl: './service-tag-update.component.html'
})
export class ServiceTagUpdateComponent implements OnInit {
  isSaving: boolean;

  servicetags: IServiceTag[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    parentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceTagService: ServiceTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceTag }) => {
      this.updateForm(serviceTag);
    });
    this.serviceTagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceTag[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceTag[]>) => response.body)
      )
      .subscribe((res: IServiceTag[]) => (this.servicetags = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceTag: IServiceTag) {
    this.editForm.patchValue({
      id: serviceTag.id,
      name: serviceTag.name,
      createdBy: serviceTag.createdBy,
      createdDateTime: serviceTag.createdDateTime != null ? serviceTag.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceTag.modifiedBy,
      modifiedDateTime: serviceTag.modifiedDateTime != null ? serviceTag.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      parentId: serviceTag.parentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceTag = this.createFromForm();
    if (serviceTag.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceTagService.update(serviceTag));
    } else {
      this.subscribeToSaveResponse(this.serviceTagService.create(serviceTag));
    }
  }

  private createFromForm(): IServiceTag {
    return {
      ...new ServiceTag(),
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
      parentId: this.editForm.get(['parentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceTag>>) {
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

  trackServiceTagById(index: number, item: IServiceTag) {
    return item.id;
  }
}
