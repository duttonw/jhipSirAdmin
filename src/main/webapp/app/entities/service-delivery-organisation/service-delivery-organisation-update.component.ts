import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceDeliveryOrganisation, ServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';
import { ServiceDeliveryOrganisationService } from './service-delivery-organisation.service';
import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from 'app/entities/agency';

@Component({
  selector: 'jhi-service-delivery-organisation-update',
  templateUrl: './service-delivery-organisation-update.component.html'
})
export class ServiceDeliveryOrganisationUpdateComponent implements OnInit {
  isSaving: boolean;

  agencies: IAgency[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    businessUnitName: [null, [Validators.maxLength(255)]],
    agencyId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceDeliveryOrganisationService: ServiceDeliveryOrganisationService,
    protected agencyService: AgencyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceDeliveryOrganisation }) => {
      this.updateForm(serviceDeliveryOrganisation);
    });
    this.agencyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgency[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgency[]>) => response.body)
      )
      .subscribe((res: IAgency[]) => (this.agencies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceDeliveryOrganisation: IServiceDeliveryOrganisation) {
    this.editForm.patchValue({
      id: serviceDeliveryOrganisation.id,
      createdBy: serviceDeliveryOrganisation.createdBy,
      createdDateTime:
        serviceDeliveryOrganisation.createdDateTime != null ? serviceDeliveryOrganisation.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceDeliveryOrganisation.modifiedBy,
      modifiedDateTime:
        serviceDeliveryOrganisation.modifiedDateTime != null ? serviceDeliveryOrganisation.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceDeliveryOrganisation.version,
      businessUnitName: serviceDeliveryOrganisation.businessUnitName,
      agencyId: serviceDeliveryOrganisation.agencyId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceDeliveryOrganisation = this.createFromForm();
    if (serviceDeliveryOrganisation.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceDeliveryOrganisationService.update(serviceDeliveryOrganisation));
    } else {
      this.subscribeToSaveResponse(this.serviceDeliveryOrganisationService.create(serviceDeliveryOrganisation));
    }
  }

  private createFromForm(): IServiceDeliveryOrganisation {
    return {
      ...new ServiceDeliveryOrganisation(),
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
      businessUnitName: this.editForm.get(['businessUnitName']).value,
      agencyId: this.editForm.get(['agencyId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceDeliveryOrganisation>>) {
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
}
