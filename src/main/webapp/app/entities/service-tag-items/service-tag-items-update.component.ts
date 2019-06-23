import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceTagItems, ServiceTagItems } from 'app/shared/model/service-tag-items.model';
import { ServiceTagItemsService } from './service-tag-items.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';
import { IServiceTag } from 'app/shared/model/service-tag.model';
import { ServiceTagService } from 'app/entities/service-tag';

@Component({
  selector: 'jhi-service-tag-items-update',
  templateUrl: './service-tag-items-update.component.html'
})
export class ServiceTagItemsUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  servicetags: IServiceTag[];

  editForm = this.fb.group({
    id: [],
    source: [null, [Validators.required, Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    serviceRecordId: [null, Validators.required],
    serviceTagId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceTagItemsService: ServiceTagItemsService,
    protected serviceRecordService: ServiceRecordService,
    protected serviceTagService: ServiceTagService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceTagItems }) => {
      this.updateForm(serviceTagItems);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceTagService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceTag[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceTag[]>) => response.body)
      )
      .subscribe((res: IServiceTag[]) => (this.servicetags = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceTagItems: IServiceTagItems) {
    this.editForm.patchValue({
      id: serviceTagItems.id,
      source: serviceTagItems.source,
      createdBy: serviceTagItems.createdBy,
      createdDateTime: serviceTagItems.createdDateTime != null ? serviceTagItems.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceTagItems.modifiedBy,
      modifiedDateTime: serviceTagItems.modifiedDateTime != null ? serviceTagItems.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      serviceRecordId: serviceTagItems.serviceRecordId,
      serviceTagId: serviceTagItems.serviceTagId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceTagItems = this.createFromForm();
    if (serviceTagItems.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceTagItemsService.update(serviceTagItems));
    } else {
      this.subscribeToSaveResponse(this.serviceTagItemsService.create(serviceTagItems));
    }
  }

  private createFromForm(): IServiceTagItems {
    return {
      ...new ServiceTagItems(),
      id: this.editForm.get(['id']).value,
      source: this.editForm.get(['source']).value,
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
      serviceRecordId: this.editForm.get(['serviceRecordId']).value,
      serviceTagId: this.editForm.get(['serviceTagId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceTagItems>>) {
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

  trackServiceTagById(index: number, item: IServiceTag) {
    return item.id;
  }
}
