import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IServiceSupportRoleContextType, ServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';
import { ServiceSupportRoleContextTypeService } from './service-support-role-context-type.service';

@Component({
  selector: 'jhi-service-support-role-context-type-update',
  templateUrl: './service-support-role-context-type-update.component.html'
})
export class ServiceSupportRoleContextTypeUpdateComponent implements OnInit {
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
    protected serviceSupportRoleContextTypeService: ServiceSupportRoleContextTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceSupportRoleContextType }) => {
      this.updateForm(serviceSupportRoleContextType);
    });
  }

  updateForm(serviceSupportRoleContextType: IServiceSupportRoleContextType) {
    this.editForm.patchValue({
      id: serviceSupportRoleContextType.id,
      createdBy: serviceSupportRoleContextType.createdBy,
      createdDateTime:
        serviceSupportRoleContextType.createdDateTime != null
          ? serviceSupportRoleContextType.createdDateTime.format(DATE_TIME_FORMAT)
          : null,
      modifiedBy: serviceSupportRoleContextType.modifiedBy,
      modifiedDateTime:
        serviceSupportRoleContextType.modifiedDateTime != null
          ? serviceSupportRoleContextType.modifiedDateTime.format(DATE_TIME_FORMAT)
          : null,
      version: serviceSupportRoleContextType.version,
      context: serviceSupportRoleContextType.context
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceSupportRoleContextType = this.createFromForm();
    if (serviceSupportRoleContextType.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceSupportRoleContextTypeService.update(serviceSupportRoleContextType));
    } else {
      this.subscribeToSaveResponse(this.serviceSupportRoleContextTypeService.create(serviceSupportRoleContextType));
    }
  }

  private createFromForm(): IServiceSupportRoleContextType {
    return {
      ...new ServiceSupportRoleContextType(),
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceSupportRoleContextType>>) {
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
