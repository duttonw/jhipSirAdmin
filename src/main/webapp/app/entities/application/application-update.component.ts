import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IApplication, Application } from 'app/shared/model/application.model';
import { ApplicationService } from './application.service';

@Component({
  selector: 'jhi-application-update',
  templateUrl: './application-update.component.html'
})
export class ApplicationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    contactEmail: [null, [Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    migratedBy: [null, [Validators.maxLength(10)]],
    version: []
  });

  constructor(protected applicationService: ApplicationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ application }) => {
      this.updateForm(application);
    });
  }

  updateForm(application: IApplication) {
    this.editForm.patchValue({
      id: application.id,
      contactEmail: application.contactEmail,
      createdBy: application.createdBy,
      createdDateTime: application.createdDateTime != null ? application.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: application.modifiedBy,
      modifiedDateTime: application.modifiedDateTime != null ? application.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      name: application.name,
      migratedBy: application.migratedBy,
      version: application.version
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const application = this.createFromForm();
    if (application.id !== undefined) {
      this.subscribeToSaveResponse(this.applicationService.update(application));
    } else {
      this.subscribeToSaveResponse(this.applicationService.create(application));
    }
  }

  private createFromForm(): IApplication {
    return {
      ...new Application(),
      id: this.editForm.get(['id']).value,
      contactEmail: this.editForm.get(['contactEmail']).value,
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
      name: this.editForm.get(['name']).value,
      migratedBy: this.editForm.get(['migratedBy']).value,
      version: this.editForm.get(['version']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IApplication>>) {
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
