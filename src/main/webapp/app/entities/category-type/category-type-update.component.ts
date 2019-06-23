import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICategoryType, CategoryType } from 'app/shared/model/category-type.model';
import { CategoryTypeService } from './category-type.service';

@Component({
  selector: 'jhi-category-type-update',
  templateUrl: './category-type-update.component.html'
})
export class CategoryTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    categoryType: [null, [Validators.required, Validators.maxLength(255)]],
    migrated: [null, [Validators.maxLength(1)]],
    migratedBy: [null, [Validators.maxLength(10)]]
  });

  constructor(protected categoryTypeService: CategoryTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categoryType }) => {
      this.updateForm(categoryType);
    });
  }

  updateForm(categoryType: ICategoryType) {
    this.editForm.patchValue({
      id: categoryType.id,
      createdBy: categoryType.createdBy,
      createdDateTime: categoryType.createdDateTime != null ? categoryType.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: categoryType.modifiedBy,
      modifiedDateTime: categoryType.modifiedDateTime != null ? categoryType.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: categoryType.version,
      categoryType: categoryType.categoryType,
      migrated: categoryType.migrated,
      migratedBy: categoryType.migratedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categoryType = this.createFromForm();
    if (categoryType.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryTypeService.update(categoryType));
    } else {
      this.subscribeToSaveResponse(this.categoryTypeService.create(categoryType));
    }
  }

  private createFromForm(): ICategoryType {
    return {
      ...new CategoryType(),
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
      categoryType: this.editForm.get(['categoryType']).value,
      migrated: this.editForm.get(['migrated']).value,
      migratedBy: this.editForm.get(['migratedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryType>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
