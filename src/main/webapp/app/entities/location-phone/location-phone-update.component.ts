import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILocationPhone, LocationPhone } from 'app/shared/model/location-phone.model';
import { LocationPhoneService } from './location-phone.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
  selector: 'jhi-location-phone-update',
  templateUrl: './location-phone-update.component.html'
})
export class LocationPhoneUpdateComponent implements OnInit {
  isSaving: boolean;

  locations: ILocation[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    comment: [null, [Validators.maxLength(255)]],
    phoneNumber: [null, [Validators.maxLength(255)]],
    locationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected locationPhoneService: LocationPhoneService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ locationPhone }) => {
      this.updateForm(locationPhone);
    });
    this.locationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe((res: ILocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(locationPhone: ILocationPhone) {
    this.editForm.patchValue({
      id: locationPhone.id,
      createdBy: locationPhone.createdBy,
      createdDateTime: locationPhone.createdDateTime != null ? locationPhone.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: locationPhone.modifiedBy,
      modifiedDateTime: locationPhone.modifiedDateTime != null ? locationPhone.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: locationPhone.version,
      comment: locationPhone.comment,
      phoneNumber: locationPhone.phoneNumber,
      locationId: locationPhone.locationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const locationPhone = this.createFromForm();
    if (locationPhone.id !== undefined) {
      this.subscribeToSaveResponse(this.locationPhoneService.update(locationPhone));
    } else {
      this.subscribeToSaveResponse(this.locationPhoneService.create(locationPhone));
    }
  }

  private createFromForm(): ILocationPhone {
    return {
      ...new LocationPhone(),
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
      comment: this.editForm.get(['comment']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      locationId: this.editForm.get(['locationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationPhone>>) {
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

  trackLocationById(index: number, item: ILocation) {
    return item.id;
  }
}
