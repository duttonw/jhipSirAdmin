import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAgencySupportRoleContextType, AgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';
import { AgencySupportRoleContextTypeService } from './agency-support-role-context-type.service';

@Component({
  selector: 'jhi-agency-support-role-context-type-update',
  templateUrl: './agency-support-role-context-type-update.component.html'
})
export class AgencySupportRoleContextTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    context: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(
    protected agencySupportRoleContextTypeService: AgencySupportRoleContextTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agencySupportRoleContextType }) => {
      this.updateForm(agencySupportRoleContextType);
    });
  }

  updateForm(agencySupportRoleContextType: IAgencySupportRoleContextType) {
    this.editForm.patchValue({
      id: agencySupportRoleContextType.id,
      createdBy: agencySupportRoleContextType.createdBy,
      createdDateTime:
        agencySupportRoleContextType.createdDateTime != null ? agencySupportRoleContextType.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: agencySupportRoleContextType.modifiedBy,
      modifiedDateTime:
        agencySupportRoleContextType.modifiedDateTime != null
          ? agencySupportRoleContextType.modifiedDateTime.format(DATE_TIME_FORMAT)
          : null,
      version: agencySupportRoleContextType.version,
      context: agencySupportRoleContextType.context
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agencySupportRoleContextType = this.createFromForm();
    if (agencySupportRoleContextType.id !== undefined) {
      this.subscribeToSaveResponse(this.agencySupportRoleContextTypeService.update(agencySupportRoleContextType));
    } else {
      this.subscribeToSaveResponse(this.agencySupportRoleContextTypeService.create(agencySupportRoleContextType));
    }
  }

  private createFromForm(): IAgencySupportRoleContextType {
    return {
      ...new AgencySupportRoleContextType(),
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
      context: this.editForm.get(['context']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgencySupportRoleContextType>>) {
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
