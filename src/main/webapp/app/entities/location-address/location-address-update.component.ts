import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILocationAddress, LocationAddress } from 'app/shared/model/location-address.model';
import { LocationAddressService } from './location-address.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
  selector: 'jhi-location-address-update',
  templateUrl: './location-address-update.component.html'
})
export class LocationAddressUpdateComponent implements OnInit {
  isSaving: boolean;

  locations: ILocation[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    additionalInformation: [null, [Validators.maxLength(255)]],
    addressLine1: [null, [Validators.maxLength(255)]],
    addressLine2: [null, [Validators.maxLength(255)]],
    addressType: [null, [Validators.required, Validators.maxLength(255)]],
    countryCode: [null, [Validators.maxLength(2)]],
    localityName: [null, [Validators.maxLength(255)]],
    locationPoint: [],
    postcode: [null, [Validators.maxLength(4)]],
    stateCode: [null, [Validators.maxLength(3)]],
    locationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected locationAddressService: LocationAddressService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ locationAddress }) => {
      this.updateForm(locationAddress);
    });
    this.locationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe((res: ILocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(locationAddress: ILocationAddress) {
    this.editForm.patchValue({
      id: locationAddress.id,
      createdBy: locationAddress.createdBy,
      createdDateTime: locationAddress.createdDateTime != null ? locationAddress.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: locationAddress.modifiedBy,
      modifiedDateTime: locationAddress.modifiedDateTime != null ? locationAddress.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: locationAddress.version,
      additionalInformation: locationAddress.additionalInformation,
      addressLine1: locationAddress.addressLine1,
      addressLine2: locationAddress.addressLine2,
      addressType: locationAddress.addressType,
      countryCode: locationAddress.countryCode,
      localityName: locationAddress.localityName,
      locationPoint: locationAddress.locationPoint,
      postcode: locationAddress.postcode,
      stateCode: locationAddress.stateCode,
      locationId: locationAddress.locationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const locationAddress = this.createFromForm();
    if (locationAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.locationAddressService.update(locationAddress));
    } else {
      this.subscribeToSaveResponse(this.locationAddressService.create(locationAddress));
    }
  }

  private createFromForm(): ILocationAddress {
    return {
      ...new LocationAddress(),
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
      additionalInformation: this.editForm.get(['additionalInformation']).value,
      addressLine1: this.editForm.get(['addressLine1']).value,
      addressLine2: this.editForm.get(['addressLine2']).value,
      addressType: this.editForm.get(['addressType']).value,
      countryCode: this.editForm.get(['countryCode']).value,
      localityName: this.editForm.get(['localityName']).value,
      locationPoint: this.editForm.get(['locationPoint']).value,
      postcode: this.editForm.get(['postcode']).value,
      stateCode: this.editForm.get(['stateCode']).value,
      locationId: this.editForm.get(['locationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationAddress>>) {
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
