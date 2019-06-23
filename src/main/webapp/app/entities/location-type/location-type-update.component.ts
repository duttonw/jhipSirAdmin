import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ILocationType, LocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';

@Component({
  selector: 'jhi-location-type-update',
  templateUrl: './location-type-update.component.html'
})
export class LocationTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    locationTypeCode: [null, [Validators.maxLength(255)]]
  });

  constructor(protected locationTypeService: LocationTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ locationType }) => {
      this.updateForm(locationType);
    });
  }

  updateForm(locationType: ILocationType) {
    this.editForm.patchValue({
      id: locationType.id,
      createdBy: locationType.createdBy,
      createdDateTime: locationType.createdDateTime != null ? locationType.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: locationType.modifiedBy,
      modifiedDateTime: locationType.modifiedDateTime != null ? locationType.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: locationType.version,
      locationTypeCode: locationType.locationTypeCode
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const locationType = this.createFromForm();
    if (locationType.id !== undefined) {
      this.subscribeToSaveResponse(this.locationTypeService.update(locationType));
    } else {
      this.subscribeToSaveResponse(this.locationTypeService.create(locationType));
    }
  }

  private createFromForm(): ILocationType {
    return {
      ...new LocationType(),
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
      locationTypeCode: this.editForm.get(['locationTypeCode']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationType>>) {
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
