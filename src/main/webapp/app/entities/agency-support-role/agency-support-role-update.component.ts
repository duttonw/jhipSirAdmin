import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAgencySupportRole, AgencySupportRole } from 'app/shared/model/agency-support-role.model';
import { AgencySupportRoleService } from './agency-support-role.service';
import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from 'app/entities/agency';
import { IServiceRoleType } from 'app/shared/model/service-role-type.model';
import { ServiceRoleTypeService } from 'app/entities/service-role-type';
import { IAgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';
import { AgencySupportRoleContextTypeService } from 'app/entities/agency-support-role-context-type';

@Component({
  selector: 'jhi-agency-support-role-update',
  templateUrl: './agency-support-role-update.component.html'
})
export class AgencySupportRoleUpdateComponent implements OnInit {
  isSaving: boolean;

  agencies: IAgency[];

  serviceroletypes: IServiceRoleType[];

  agencysupportrolecontexttypes: IAgencySupportRoleContextType[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    contactEmail: [null, [Validators.maxLength(255)]],
    agencyId: [],
    agencyRoleTypeId: [],
    agencySupportContextTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agencySupportRoleService: AgencySupportRoleService,
    protected agencyService: AgencyService,
    protected serviceRoleTypeService: ServiceRoleTypeService,
    protected agencySupportRoleContextTypeService: AgencySupportRoleContextTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agencySupportRole }) => {
      this.updateForm(agencySupportRole);
    });
    this.agencyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgency[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgency[]>) => response.body)
      )
      .subscribe((res: IAgency[]) => (this.agencies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceRoleTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRoleType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRoleType[]>) => response.body)
      )
      .subscribe((res: IServiceRoleType[]) => (this.serviceroletypes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.agencySupportRoleContextTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgencySupportRoleContextType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgencySupportRoleContextType[]>) => response.body)
      )
      .subscribe(
        (res: IAgencySupportRoleContextType[]) => (this.agencysupportrolecontexttypes = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(agencySupportRole: IAgencySupportRole) {
    this.editForm.patchValue({
      id: agencySupportRole.id,
      createdBy: agencySupportRole.createdBy,
      createdDateTime: agencySupportRole.createdDateTime != null ? agencySupportRole.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: agencySupportRole.modifiedBy,
      modifiedDateTime: agencySupportRole.modifiedDateTime != null ? agencySupportRole.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: agencySupportRole.version,
      contactEmail: agencySupportRole.contactEmail,
      agencyId: agencySupportRole.agencyId,
      agencyRoleTypeId: agencySupportRole.agencyRoleTypeId,
      agencySupportContextTypeId: agencySupportRole.agencySupportContextTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agencySupportRole = this.createFromForm();
    if (agencySupportRole.id !== undefined) {
      this.subscribeToSaveResponse(this.agencySupportRoleService.update(agencySupportRole));
    } else {
      this.subscribeToSaveResponse(this.agencySupportRoleService.create(agencySupportRole));
    }
  }

  private createFromForm(): IAgencySupportRole {
    return {
      ...new AgencySupportRole(),
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
      agencyId: this.editForm.get(['agencyId']).value,
      agencyRoleTypeId: this.editForm.get(['agencyRoleTypeId']).value,
      agencySupportContextTypeId: this.editForm.get(['agencySupportContextTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgencySupportRole>>) {
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

  trackAgencyById(index: number, item: IAgency) {
    return item.id;
  }

  trackServiceRoleTypeById(index: number, item: IServiceRoleType) {
    return item.id;
  }

  trackAgencySupportRoleContextTypeById(index: number, item: IAgencySupportRoleContextType) {
    return item.id;
  }
}
