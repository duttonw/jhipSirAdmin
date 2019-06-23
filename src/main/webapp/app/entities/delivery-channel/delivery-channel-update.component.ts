import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDeliveryChannel, DeliveryChannel } from 'app/shared/model/delivery-channel.model';
import { DeliveryChannelService } from './delivery-channel.service';
import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';
import { AvailabilityHoursService } from 'app/entities/availability-hours';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';
import { IServiceDelivery } from 'app/shared/model/service-delivery.model';
import { ServiceDeliveryService } from 'app/entities/service-delivery';

@Component({
  selector: 'jhi-delivery-channel-update',
  templateUrl: './delivery-channel-update.component.html'
})
export class DeliveryChannelUpdateComponent implements OnInit {
  isSaving: boolean;

  availabilityhours: IAvailabilityHours[];

  locations: ILocation[];

  servicedeliveries: IServiceDelivery[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    additionalDetails: [null, [Validators.maxLength(255)]],
    deliveryChannelType: [null, [Validators.required, Validators.maxLength(255)]],
    deliveryChannelId: [],
    virtualDeliveryDetails: [null, [Validators.maxLength(255)]],
    deliveryHoursId: [],
    locationId: [],
    serviceDeliveryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected deliveryChannelService: DeliveryChannelService,
    protected availabilityHoursService: AvailabilityHoursService,
    protected locationService: LocationService,
    protected serviceDeliveryService: ServiceDeliveryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ deliveryChannel }) => {
      this.updateForm(deliveryChannel);
    });
    this.availabilityHoursService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAvailabilityHours[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAvailabilityHours[]>) => response.body)
      )
      .subscribe((res: IAvailabilityHours[]) => (this.availabilityhours = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.locationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe((res: ILocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.serviceDeliveryService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IServiceDelivery[]>) => mayBeOk.ok),
        map((response: HttpResponse<IServiceDelivery[]>) => response.body)
      )
      .subscribe((res: IServiceDelivery[]) => (this.servicedeliveries = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(deliveryChannel: IDeliveryChannel) {
    this.editForm.patchValue({
      id: deliveryChannel.id,
      createdBy: deliveryChannel.createdBy,
      createdDateTime: deliveryChannel.createdDateTime != null ? deliveryChannel.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: deliveryChannel.modifiedBy,
      modifiedDateTime: deliveryChannel.modifiedDateTime != null ? deliveryChannel.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: deliveryChannel.version,
      additionalDetails: deliveryChannel.additionalDetails,
      deliveryChannelType: deliveryChannel.deliveryChannelType,
      deliveryChannelId: deliveryChannel.deliveryChannelId,
      virtualDeliveryDetails: deliveryChannel.virtualDeliveryDetails,
      deliveryHoursId: deliveryChannel.deliveryHoursId,
      locationId: deliveryChannel.locationId,
      serviceDeliveryId: deliveryChannel.serviceDeliveryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const deliveryChannel = this.createFromForm();
    if (deliveryChannel.id !== undefined) {
      this.subscribeToSaveResponse(this.deliveryChannelService.update(deliveryChannel));
    } else {
      this.subscribeToSaveResponse(this.deliveryChannelService.create(deliveryChannel));
    }
  }

  private createFromForm(): IDeliveryChannel {
    return {
      ...new DeliveryChannel(),
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
      additionalDetails: this.editForm.get(['additionalDetails']).value,
      deliveryChannelType: this.editForm.get(['deliveryChannelType']).value,
      deliveryChannelId: this.editForm.get(['deliveryChannelId']).value,
      virtualDeliveryDetails: this.editForm.get(['virtualDeliveryDetails']).value,
      deliveryHoursId: this.editForm.get(['deliveryHoursId']).value,
      locationId: this.editForm.get(['locationId']).value,
      serviceDeliveryId: this.editForm.get(['serviceDeliveryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryChannel>>) {
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

  trackAvailabilityHoursById(index: number, item: IAvailabilityHours) {
    return item.id;
  }

  trackLocationById(index: number, item: ILocation) {
    return item.id;
  }

  trackServiceDeliveryById(index: number, item: IServiceDelivery) {
    return item.id;
  }
}
