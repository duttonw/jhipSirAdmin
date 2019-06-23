import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IServiceRoleType, ServiceRoleType } from 'app/shared/model/service-role-type.model';
import { ServiceRoleTypeService } from './service-role-type.service';

@Component({
  selector: 'jhi-service-role-type-update',
  templateUrl: './service-role-type-update.component.html'
})
export class ServiceRoleTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    serviceRole: [null, [Validators.maxLength(255)]]
  });

  constructor(
    protected serviceRoleTypeService: ServiceRoleTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceRoleType }) => {
      this.updateForm(serviceRoleType);
    });
  }

  updateForm(serviceRoleType: IServiceRoleType) {
    this.editForm.patchValue({
      id: serviceRoleType.id,
      createdBy: serviceRoleType.createdBy,
      createdDateTime: serviceRoleType.createdDateTime != null ? serviceRoleType.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceRoleType.modifiedBy,
      modifiedDateTime: serviceRoleType.modifiedDateTime != null ? serviceRoleType.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceRoleType.version,
      serviceRole: serviceRoleType.serviceRole
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceRoleType = this.createFromForm();
    if (serviceRoleType.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceRoleTypeService.update(serviceRoleType));
    } else {
      this.subscribeToSaveResponse(this.serviceRoleTypeService.create(serviceRoleType));
    }
  }

  private createFromForm(): IServiceRoleType {
    return {
      ...new ServiceRoleType(),
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
      serviceRole: this.editForm.get(['serviceRole']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRoleType>>) {
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
