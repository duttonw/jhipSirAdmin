import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IIntegrationMapping, IntegrationMapping } from 'app/shared/model/integration-mapping.model';
import { IntegrationMappingService } from './integration-mapping.service';
import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from 'app/entities/agency';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-integration-mapping-update',
  templateUrl: './integration-mapping-update.component.html'
})
export class IntegrationMappingUpdateComponent implements OnInit {
  isSaving: boolean;

  agencies: IAgency[];

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    agencyServiceId: [null, [Validators.required, Validators.maxLength(255)]],
    serviceName: [null, [Validators.required, Validators.maxLength(255)]],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    agencyId: [null, Validators.required],
    serviceRecordId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected integrationMappingService: IntegrationMappingService,
    protected agencyService: AgencyService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ integrationMapping }) => {
      this.updateForm(integrationMapping);
    });
    this.agencyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgency[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgency[]>) => response.body)
      )
      .subscribe((res: IAgency[]) => (this.agencies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(integrationMapping: IIntegrationMapping) {
    this.editForm.patchValue({
      id: integrationMapping.id,
      agencyServiceId: integrationMapping.agencyServiceId,
      serviceName: integrationMapping.serviceName,
      createdBy: integrationMapping.createdBy,
      createdDateTime: integrationMapping.createdDateTime != null ? integrationMapping.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: integrationMapping.modifiedBy,
      modifiedDateTime: integrationMapping.modifiedDateTime != null ? integrationMapping.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      agencyId: integrationMapping.agencyId,
      serviceRecordId: integrationMapping.serviceRecordId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const integrationMapping = this.createFromForm();
    if (integrationMapping.id !== undefined) {
      this.subscribeToSaveResponse(this.integrationMappingService.update(integrationMapping));
    } else {
      this.subscribeToSaveResponse(this.integrationMappingService.create(integrationMapping));
    }
  }

  private createFromForm(): IIntegrationMapping {
    return {
      ...new IntegrationMapping(),
      id: this.editForm.get(['id']).value,
      agencyServiceId: this.editForm.get(['agencyServiceId']).value,
      serviceName: this.editForm.get(['serviceName']).value,
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
      agencyId: this.editForm.get(['agencyId']).value,
      serviceRecordId: this.editForm.get(['serviceRecordId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIntegrationMapping>>) {
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

  trackServiceRecordById(index: number, item: IServiceRecord) {
    return item.id;
  }
}
