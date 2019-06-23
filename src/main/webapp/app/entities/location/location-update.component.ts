import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILocation, Location } from 'app/shared/model/location.model';
import { LocationService } from './location.service';
import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from 'app/entities/agency';
import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';
import { AvailabilityHoursService } from 'app/entities/availability-hours';
import { ILocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from 'app/entities/location-type';

@Component({
  selector: 'jhi-location-update',
  templateUrl: './location-update.component.html'
})
export class LocationUpdateComponent implements OnInit {
  isSaving: boolean;

  agencies: IAgency[];

  availabilityhours: IAvailabilityHours[];

  locationtypes: ILocationType[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    accessibilityFacilities: [null, [Validators.maxLength(255)]],
    additionalInformation: [null, [Validators.maxLength(255)]],
    locationName: [null, [Validators.required, Validators.maxLength(255)]],
    agencyId: [],
    locationHoursId: [],
    locationTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected locationService: LocationService,
    protected agencyService: AgencyService,
    protected availabilityHoursService: AvailabilityHoursService,
    protected locationTypeService: LocationTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ location }) => {
      this.updateForm(location);
    });
    this.agencyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgency[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgency[]>) => response.body)
      )
      .subscribe((res: IAgency[]) => (this.agencies = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.availabilityHoursService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAvailabilityHours[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAvailabilityHours[]>) => response.body)
      )
      .subscribe((res: IAvailabilityHours[]) => (this.availabilityhours = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.locationTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocationType[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocationType[]>) => response.body)
      )
      .subscribe((res: ILocationType[]) => (this.locationtypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(location: ILocation) {
    this.editForm.patchValue({
      id: location.id,
      createdBy: location.createdBy,
      createdDateTime: location.createdDateTime != null ? location.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: location.modifiedBy,
      modifiedDateTime: location.modifiedDateTime != null ? location.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: location.version,
      accessibilityFacilities: location.accessibilityFacilities,
      additionalInformation: location.additionalInformation,
      locationName: location.locationName,
      agencyId: location.agencyId,
      locationHoursId: location.locationHoursId,
      locationTypeId: location.locationTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const location = this.createFromForm();
    if (location.id !== undefined) {
      this.subscribeToSaveResponse(this.locationService.update(location));
    } else {
      this.subscribeToSaveResponse(this.locationService.create(location));
    }
  }

  private createFromForm(): ILocation {
    return {
      ...new Location(),
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
      accessibilityFacilities: this.editForm.get(['accessibilityFacilities']).value,
      additionalInformation: this.editForm.get(['additionalInformation']).value,
      locationName: this.editForm.get(['locationName']).value,
      agencyId: this.editForm.get(['agencyId']).value,
      locationHoursId: this.editForm.get(['locationHoursId']).value,
      locationTypeId: this.editForm.get(['locationTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>) {
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

  trackAvailabilityHoursById(index: number, item: IAvailabilityHours) {
    return item.id;
  }

  trackLocationTypeById(index: number, item: ILocationType) {
    return item.id;
  }
}
