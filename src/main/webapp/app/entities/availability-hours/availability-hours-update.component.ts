import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IAvailabilityHours, AvailabilityHours } from 'app/shared/model/availability-hours.model';
import { AvailabilityHoursService } from './availability-hours.service';

@Component({
  selector: 'jhi-availability-hours-update',
  templateUrl: './availability-hours-update.component.html'
})
export class AvailabilityHoursUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    available: [null, [Validators.maxLength(1)]],
    availabilityHoursId: [],
    comments: [null, [Validators.maxLength(255)]],
    validFrom: [],
    validTo: []
  });

  constructor(
    protected availabilityHoursService: AvailabilityHoursService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ availabilityHours }) => {
      this.updateForm(availabilityHours);
    });
  }

  updateForm(availabilityHours: IAvailabilityHours) {
    this.editForm.patchValue({
      id: availabilityHours.id,
      createdBy: availabilityHours.createdBy,
      createdDateTime: availabilityHours.createdDateTime != null ? availabilityHours.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: availabilityHours.modifiedBy,
      modifiedDateTime: availabilityHours.modifiedDateTime != null ? availabilityHours.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: availabilityHours.version,
      available: availabilityHours.available,
      availabilityHoursId: availabilityHours.availabilityHoursId,
      comments: availabilityHours.comments,
      validFrom: availabilityHours.validFrom != null ? availabilityHours.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: availabilityHours.validTo != null ? availabilityHours.validTo.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const availabilityHours = this.createFromForm();
    if (availabilityHours.id !== undefined) {
      this.subscribeToSaveResponse(this.availabilityHoursService.update(availabilityHours));
    } else {
      this.subscribeToSaveResponse(this.availabilityHoursService.create(availabilityHours));
    }
  }

  private createFromForm(): IAvailabilityHours {
    return {
      ...new AvailabilityHours(),
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
      available: this.editForm.get(['available']).value,
      availabilityHoursId: this.editForm.get(['availabilityHoursId']).value,
      comments: this.editForm.get(['comments']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvailabilityHours>>) {
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
