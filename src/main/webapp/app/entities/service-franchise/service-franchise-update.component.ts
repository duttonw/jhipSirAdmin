import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IServiceFranchise, ServiceFranchise } from 'app/shared/model/service-franchise.model';
import { ServiceFranchiseService } from './service-franchise.service';

@Component({
  selector: 'jhi-service-franchise-update',
  templateUrl: './service-franchise-update.component.html'
})
export class ServiceFranchiseUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    franchiseName: [null, [Validators.maxLength(255)]],
    version: []
  });

  constructor(
    protected serviceFranchiseService: ServiceFranchiseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceFranchise }) => {
      this.updateForm(serviceFranchise);
    });
  }

  updateForm(serviceFranchise: IServiceFranchise) {
    this.editForm.patchValue({
      id: serviceFranchise.id,
      createdBy: serviceFranchise.createdBy,
      createdDateTime: serviceFranchise.createdDateTime != null ? serviceFranchise.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceFranchise.modifiedBy,
      modifiedDateTime: serviceFranchise.modifiedDateTime != null ? serviceFranchise.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      franchiseName: serviceFranchise.franchiseName,
      version: serviceFranchise.version
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceFranchise = this.createFromForm();
    if (serviceFranchise.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceFranchiseService.update(serviceFranchise));
    } else {
      this.subscribeToSaveResponse(this.serviceFranchiseService.create(serviceFranchise));
    }
  }

  private createFromForm(): IServiceFranchise {
    return {
      ...new ServiceFranchise(),
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
      franchiseName: this.editForm.get(['franchiseName']).value,
      version: this.editForm.get(['version']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceFranchise>>) {
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
