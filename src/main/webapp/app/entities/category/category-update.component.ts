import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    category: [null, [Validators.required, Validators.maxLength(255)]],
    migrated: [null, [Validators.maxLength(1)]],
    migratedBy: [null, [Validators.maxLength(10)]]
  });

  constructor(protected categoryService: CategoryService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ category }) => {
      this.updateForm(category);
    });
  }

  updateForm(category: ICategory) {
    this.editForm.patchValue({
      id: category.id,
      createdBy: category.createdBy,
      createdDateTime: category.createdDateTime != null ? category.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: category.modifiedBy,
      modifiedDateTime: category.modifiedDateTime != null ? category.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: category.version,
      category: category.category,
      migrated: category.migrated,
      migratedBy: category.migratedBy
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  private createFromForm(): ICategory {
    return {
      ...new Category(),
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
      category: this.editForm.get(['category']).value,
      migrated: this.editForm.get(['migrated']).value,
      migratedBy: this.editForm.get(['migratedBy']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>) {
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
