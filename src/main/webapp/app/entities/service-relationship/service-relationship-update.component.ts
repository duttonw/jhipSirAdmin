import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IServiceRelationship, ServiceRelationship } from 'app/shared/model/service-relationship.model';
import { ServiceRelationshipService } from './service-relationship.service';
import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from 'app/entities/service-record';

@Component({
  selector: 'jhi-service-relationship-update',
  templateUrl: './service-relationship-update.component.html'
})
export class ServiceRelationshipUpdateComponent implements OnInit {
  isSaving: boolean;

  servicerecords: IServiceRecord[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    relationship: [null, [Validators.maxLength(255)]],
    fromServiceId: [],
    toServiceId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected serviceRelationshipService: ServiceRelationshipService,
    protected serviceRecordService: ServiceRecordService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceRelationship }) => {
      this.updateForm(serviceRelationship);
    });
    this.serviceRecordService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceRecord[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceRecord[]>) => response.body)
      )
      .subscribe((res: IServiceRecord[]) => (this.servicerecords = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(serviceRelationship: IServiceRelationship) {
    this.editForm.patchValue({
      id: serviceRelationship.id,
      createdBy: serviceRelationship.createdBy,
      createdDateTime: serviceRelationship.createdDateTime != null ? serviceRelationship.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: serviceRelationship.modifiedBy,
      modifiedDateTime: serviceRelationship.modifiedDateTime != null ? serviceRelationship.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: serviceRelationship.version,
      relationship: serviceRelationship.relationship,
      fromServiceId: serviceRelationship.fromServiceId,
      toServiceId: serviceRelationship.toServiceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceRelationship = this.createFromForm();
    if (serviceRelationship.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceRelationshipService.update(serviceRelationship));
    } else {
      this.subscribeToSaveResponse(this.serviceRelationshipService.create(serviceRelationship));
    }
  }

  private createFromForm(): IServiceRelationship {
    return {
      ...new ServiceRelationship(),
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
      relationship: this.editForm.get(['relationship']).value,
      fromServiceId: this.editForm.get(['fromServiceId']).value,
      toServiceId: this.editForm.get(['toServiceId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceRelationship>>) {
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
}
