import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceSupportRole, ServiceSupportRole } from 'app/shared/model/service-support-role.model';
import { ServiceSupportRoleService } from './service-support-role.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';
import { IServiceRoleType } from 'app/shared/model/service-role-type.model';
import { ServiceRoleTypeService } from 'app/entities/service-role-type';
import { IServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';
import { ServiceSupportRoleContextTypeService } from 'app/entities/service-support-role-context-type';

@Component({
  selector: 'jhi-service-support-role-update',
  templateUrl: './service-support-role-update.component.html'
})
export class ServiceSupportRoleUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  serviceroletypes: IServiceRoleType[];

  servicesupportrolecontexttypes: IServiceSupportRoleContextType[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    contactEmail: [null, [Validators.maxLength(255)]],
    contactName: [null, [Validators.maxLength(255)]],
    contactPhoneNumber: [null, [Validators.maxLength(255)]],
    serviceRecordId: [],
    serviceRoleTypeId: [],
    serviceSupportContextTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceSupportRoleService: ServiceSupportRoleService,
    protected serviceRecordService: ServiceRecordService,
    protected serviceRoleTypeService: ServiceRoleTypeService,
    protected serviceSupportRoleContextTypeService: ServiceSupportRoleContextTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceSupportRole }) => {
      this.updateForm(serviceSupportRole);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceRoleTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRoleType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRoleType[]>) => response.body)
      )
      .subscribe((res: IServiceRoleType[]) => (this.serviceroletypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceSupportRoleContextTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceSupportRoleContextType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceSupportRoleContextType[]>) => response.body)
      )
      .subscribe(
        (res: IServiceSupportRoleContextType[]) => (this.servicesupportrolecontexttypes = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(serviceSupportRole: IServiceSupportRole) {
    this.editForm.patchValue({
      id: serviceSupportRole.id,
      createdBy: serviceSupportRole.createdBy,
      createdDateTime: serviceSupportRole.createdDateTime != null ? serviceSupportRole.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceSupportRole.modifiedBy,
      modifiedDateTime: serviceSupportRole.modifiedDateTime != null ? serviceSupportRole.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceSupportRole.version,
      contactEmail: serviceSupportRole.contactEmail,
      contactName: serviceSupportRole.contactName,
      contactPhoneNumber: serviceSupportRole.contactPhoneNumber,
      serviceRecordId: serviceSupportRole.serviceRecordId,
      serviceRoleTypeId: serviceSupportRole.serviceRoleTypeId,
      serviceSupportContextTypeId: serviceSupportRole.serviceSupportContextTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceSupportRole = this.createFromForm();
    if (serviceSupportRole.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceSupportRoleService.update(serviceSupportRole));
    } else {
      this.subscribeToSaveResponse(this.serviceSupportRoleService.create(serviceSupportRole));
    }
  }

  private createFromForm(): IServiceSupportRole {
    return {
      ...new ServiceSupportRole(),
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
      contactEmail: this.editForm.get(['contactEmail']).value,
      contactName: this.editForm.get(['contactName']).value,
      contactPhoneNumber: this.editForm.get(['contactPhoneNumber']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value,
      serviceRoleTypeId: this.editForm.get(['serviceRoleTypeId']).value,
      serviceSupportContextTypeId: this.editForm.get(['serviceSupportContextTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceSupportRole>>) {
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

  trackServiceRoleTypeById(index: number, item: IServiceRoleType) {
    return item.id;
  }

  trackServiceSupportRoleContextTypeById(index: number, item: IServiceSupportRoleContextType) {
    return item.id;
  }
}
