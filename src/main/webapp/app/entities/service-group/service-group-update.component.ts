import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceGroup, ServiceGroup } from 'app/shared/model/service-group.model';
import { ServiceGroupService } from './service-group.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category';
import { ICategoryType } from 'app/shared/model/category-type.model';
import { CategoryTypeService } from 'app/entities/category-type';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-group-update',
  templateUrl: './service-group-update.component.html'
})
export class ServiceGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  categories: ICategory[];

  categorytypes: ICategoryType[];

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    migrated: [null, [Validators.maxLength(1)]],
    migratedBy: [null, [Validators.maxLength(10)]],
    serviceGroupCategoryId: [],
    serviceGroupCategoryTypeId: [],
    serviceRecordId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceGroupService: ServiceGroupService,
    protected categoryService: CategoryService,
    protected categoryTypeService: CategoryTypeService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceGroup }) => {
      this.updateForm(serviceGroup);
    });
    this.categoryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategory[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategory[]>) => response.body)
      )
      .subscribe((res: ICategory[]) => (this.categories = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categoryTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoryType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoryType[]>) => response.body)
      )
      .subscribe((res: ICategoryType[]) => (this.categorytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceGroup: IServiceGroup) {
    this.editForm.patchValue({
      id: serviceGroup.id,
      createdBy: serviceGroup.createdBy,
      createdDateTime: serviceGroup.createdDateTime != null ? serviceGroup.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceGroup.modifiedBy,
      modifiedDateTime: serviceGroup.modifiedDateTime != null ? serviceGroup.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceGroup.version,
      migrated: serviceGroup.migrated,
      migratedBy: serviceGroup.migratedBy,
      serviceGroupCategoryId: serviceGroup.serviceGroupCategoryId,
      serviceGroupCategoryTypeId: serviceGroup.serviceGroupCategoryTypeId,
      serviceRecordId: serviceGroup.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceGroup = this.createFromForm();
    if (serviceGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceGroupService.update(serviceGroup));
    } else {
      this.subscribeToSaveResponse(this.serviceGroupService.create(serviceGroup));
    }
  }

  private createFromForm(): IServiceGroup {
    return {
      ...new ServiceGroup(),
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
      migrated: this.editForm.get(['migrated']).value,
      migratedBy: this.editForm.get(['migratedBy']).value,
      serviceGroupCategoryId: this.editForm.get(['serviceGroupCategoryId']).value,
      serviceGroupCategoryTypeId: this.editForm.get(['serviceGroupCategoryTypeId']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceGroup>>) {
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

  trackCategoryTypeById(index: number, item: ICategoryType) {
    return item.id;
  }

  trackServiceRecordById(index: number, item: IServiceRecord) {
    return item.id;
  }
}
