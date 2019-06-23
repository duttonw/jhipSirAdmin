import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceRecord, ServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from './service-record.service';
import { IServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';
import { ServiceDeliveryOrganisationService } from 'app/entities/service-delivery-organisation';
import { IServiceFranchise } from 'app/shared/model/service-franchise.model';
import { ServiceFranchiseService } from 'app/entities/service-franchise';

@Component({
  selector: 'jhi-service-record-update',
  templateUrl: './service-record-update.component.html'
})
export class ServiceRecordUpdateComponent implements OnInit {
  isSaving: boolean;

  servicedeliveryorganisations: IServiceDeliveryOrganisation[];

  servicerecords: IServiceRecord[];

  servicefranchises: IServiceFranchise[];
  startDateDp: any;
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    active: [null, [Validators.maxLength(1)]],
    eligibility: [null, [Validators.maxLength(255)]],
    fees: [null, [Validators.maxLength(255)]],
    groupHeader: [null, [Validators.maxLength(255)]],
    groupId: [null, [Validators.maxLength(255)]],
    interactionId: [null, [Validators.maxLength(255)]],
    keywords: [null, [Validators.maxLength(255)]],
    preRequisites: [null, [Validators.maxLength(255)]],
    qgsServiceId: [null, [Validators.maxLength(255)]],
    referenceUrl: [null, [Validators.maxLength(255)]],
    serviceName: [null, [Validators.maxLength(255)]],
    validatedDate: [],
    description: [null, [Validators.maxLength(255)]],
    preRequisitesNew: [null, [Validators.maxLength(255)]],
    referenceUrlNew: [null, [Validators.maxLength(255)]],
    eligibilityNew: [null, [Validators.maxLength(255)]],
    serviceContext: [null, [Validators.maxLength(255)]],
    longDescription: [null, [Validators.maxLength(255)]],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    startDate: [],
    endDate: [],
    roadmapLoginRequired: [null, [Validators.maxLength(1)]],
    roadmapCustomerIdRequired: [null, [Validators.maxLength(1)]],
    roadmapCustomerDetails: [null, [Validators.maxLength(1)]],
    roadmapImproveIntention: [null, [Validators.maxLength(1)]],
    roadmapImproveFuture: [null, [Validators.maxLength(1)]],
    roadmapImproveType: [null, [Validators.maxLength(255)]],
    roadmapImproveWhen: [null, [Validators.maxLength(255)]],
    roadmapImproveHow: [null, [Validators.maxLength(255)]],
    roadmapMaturityCurrent: [null, [Validators.maxLength(255)]],
    roadmapMaturityDesired: [null, [Validators.maxLength(255)]],
    roadmapComments: [null, [Validators.maxLength(255)]],
    howTo: [null, [Validators.maxLength(255)]],
    deliveryOrgId: [],
    parentServiceId: [],
    serviceFranchiseId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceRecordService: ServiceRecordService,
    protected serviceDeliveryOrganisationService: ServiceDeliveryOrganisationService,
    protected serviceFranchiseService: ServiceFranchiseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceRecord }) => {
      this.updateForm(serviceRecord);
    });
    this.serviceDeliveryOrganisationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceDeliveryOrganisation[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceDeliveryOrganisation[]>) => response.body)
      )
      .subscribe(
        (res: IServiceDeliveryOrganisation[]) => (this.servicedeliveryorganisations = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceFranchiseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceFranchise[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceFranchise[]>) => response.body)
      )
      .subscribe((res: IServiceFranchise[]) => (this.servicefranchises = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceRecord: IServiceRecord) {
    this.editForm.patchValue({
      id: serviceRecord.id,
      createdBy: serviceRecord.createdBy,
      createdDateTime: serviceRecord.createdDateTime != null ? serviceRecord.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceRecord.modifiedBy,
      modifiedDateTime: serviceRecord.modifiedDateTime != null ? serviceRecord.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceRecord.version,
      active: serviceRecord.active,
      eligibility: serviceRecord.eligibility,
      fees: serviceRecord.fees,
      groupHeader: serviceRecord.groupHeader,
      groupId: serviceRecord.groupId,
      interactionId: serviceRecord.interactionId,
      keywords: serviceRecord.keywords,
      preRequisites: serviceRecord.preRequisites,
      qgsServiceId: serviceRecord.qgsServiceId,
      referenceUrl: serviceRecord.referenceUrl,
      serviceName: serviceRecord.serviceName,
      validatedDate: serviceRecord.validatedDate != null ? serviceRecord.validatedDate.format(DATE_TIME_FORMAT) : null,
      description: serviceRecord.description,
      preRequisitesNew: serviceRecord.preRequisitesNew,
      referenceUrlNew: serviceRecord.referenceUrlNew,
      eligibilityNew: serviceRecord.eligibilityNew,
      serviceContext: serviceRecord.serviceContext,
      longDescription: serviceRecord.longDescription,
      name: serviceRecord.name,
      startDate: serviceRecord.startDate,
      endDate: serviceRecord.endDate,
      roadmapLoginRequired: serviceRecord.roadmapLoginRequired,
      roadmapCustomerIdRequired: serviceRecord.roadmapCustomerIdRequired,
      roadmapCustomerDetails: serviceRecord.roadmapCustomerDetails,
      roadmapImproveIntention: serviceRecord.roadmapImproveIntention,
      roadmapImproveFuture: serviceRecord.roadmapImproveFuture,
      roadmapImproveType: serviceRecord.roadmapImproveType,
      roadmapImproveWhen: serviceRecord.roadmapImproveWhen,
      roadmapImproveHow: serviceRecord.roadmapImproveHow,
      roadmapMaturityCurrent: serviceRecord.roadmapMaturityCurrent,
      roadmapMaturityDesired: serviceRecord.roadmapMaturityDesired,
      roadmapComments: serviceRecord.roadmapComments,
      howTo: serviceRecord.howTo,
      deliveryOrgId: serviceRecord.deliveryOrgId,
      parentServiceId: serviceRecord.parentServiceId,
      serviceFranchiseId: serviceRecord.serviceFranchiseId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceRecord = this.createFromForm();
    if (serviceRecord.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceRecordService.update(serviceRecord));
    } else {
      this.subscribeToSaveResponse(this.serviceRecordService.create(serviceRecord));
    }
  }

  private createFromForm(): IServiceRecord {
    return {
      ...new ServiceRecord(),
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
      active: this.editForm.get(['active']).value,
      eligibility: this.editForm.get(['eligibility']).value,
      fees: this.editForm.get(['fees']).value,
      groupHeader: this.editForm.get(['groupHeader']).value,
      groupId: this.editForm.get(['groupId']).value,
      interactionId: this.editForm.get(['interactionId']).value,
      keywords: this.editForm.get(['keywords']).value,
      preRequisites: this.editForm.get(['preRequisites']).value,
      qgsServiceId: this.editForm.get(['qgsServiceId']).value,
      referenceUrl: this.editForm.get(['referenceUrl']).value,
      serviceName: this.editForm.get(['serviceName']).value,
      validatedDate:
        this.editForm.get(['validatedDate']).value != null
          ? moment(this.editForm.get(['validatedDate']).value, DATE_TIME_FORMAT)
          : undefined,
      description: this.editForm.get(['description']).value,
      preRequisitesNew: this.editForm.get(['preRequisitesNew']).value,
      referenceUrlNew: this.editForm.get(['referenceUrlNew']).value,
      eligibilityNew: this.editForm.get(['eligibilityNew']).value,
      serviceContext: this.editForm.get(['serviceContext']).value,
      longDescription: this.editForm.get(['longDescription']).value,
      name: this.editForm.get(['name']).value,
      startDate: this.editForm.get(['startDate']).value,
      endDate: this.editForm.get(['endDate']).value,
      roadmapLoginRequired: this.editForm.get(['roadmapLoginRequired']).value,
      roadmapCustomerIdRequired: this.editForm.get(['roadmapCustomerIdRequired']).value,
      roadmapCustomerDetails: this.editForm.get(['roadmapCustomerDetails']).value,
      roadmapImproveIntention: this.editForm.get(['roadmapImproveIntention']).value,
      roadmapImproveFuture: this.editForm.get(['roadmapImproveFuture']).value,
      roadmapImproveType: this.editForm.get(['roadmapImproveType']).value,
      roadmapImproveWhen: this.editForm.get(['roadmapImproveWhen']).value,
      roadmapImproveHow: this.editForm.get(['roadmapImproveHow']).value,
      roadmapMaturityCurrent: this.editForm.get(['roadmapMaturityCurrent']).value,
      roadmapMaturityDesired: this.editForm.get(['roadmapMaturityDesired']).value,
      roadmapComments: this.editForm.get(['roadmapComments']).value,
      howTo: this.editForm.get(['howTo']).value,
      deliveryOrgId: this.editForm.get(['deliveryOrgId']).value,
      parentServiceId: this.editForm.get(['parentServiceId']).value,
      serviceFranchiseId: this.editForm.get(['serviceFranchiseId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRecord>>) {
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

  trackServiceDeliveryOrganisationById(index: number, item: IServiceDeliveryOrganisation) {
    return item.id;
  }

  trackServiceRecordById(index: number, item: IServiceRecord) {
    return item.id;
  }

  trackServiceFranchiseById(index: number, item: IServiceFranchise) {
    return item.id;
  }
}
