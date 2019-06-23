import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAgencyType, AgencyType } from 'app/shared/model/agency-type.model';
import { AgencyTypeService } from './agency-type.service';

@Component({
  selector: 'jhi-agency-type-update',
  templateUrl: './agency-type-update.component.html'
})
export class AgencyTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    agencyTypeName: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(protected agencyTypeService: AgencyTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agencyType }) => {
      this.updateForm(agencyType);
    });
  }

  updateForm(agencyType: IAgencyType) {
    this.editForm.patchValue({
      id: agencyType.id,
      createdBy: agencyType.createdBy,
      createdDateTime: agencyType.createdDateTime != null ? agencyType.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: agencyType.modifiedBy,
      modifiedDateTime: agencyType.modifiedDateTime != null ? agencyType.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: agencyType.version,
      agencyTypeName: agencyType.agencyTypeName
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agencyType = this.createFromForm();
    if (agencyType.id !== undefined) {
      this.subscribeToSaveResponse(this.agencyTypeService.update(agencyType));
    } else {
      this.subscribeToSaveResponse(this.agencyTypeService.create(agencyType));
    }
  }

  private createFromForm(): IAgencyType {
    return {
      ...new AgencyType(),
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
      agencyTypeName: this.editForm.get(['agencyTypeName']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgencyType>>) {
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
