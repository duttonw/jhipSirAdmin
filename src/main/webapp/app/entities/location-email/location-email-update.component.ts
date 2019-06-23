import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILocationEmail, LocationEmail } from 'app/shared/model/location-email.model';
import { LocationEmailService } from './location-email.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
  selector: 'jhi-location-email-update',
  templateUrl: './location-email-update.component.html'
})
export class LocationEmailUpdateComponent implements OnInit {
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
    email: [null, [Validators.maxLength(255)]],
    locationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected locationEmailService: LocationEmailService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ locationEmail }) => {
      this.updateForm(locationEmail);
    });
    this.locationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe((res: ILocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(locationEmail: ILocationEmail) {
    this.editForm.patchValue({
      id: locationEmail.id,
      createdBy: locationEmail.createdBy,
      createdDateTime: locationEmail.createdDateTime != null ? locationEmail.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: locationEmail.modifiedBy,
      modifiedDateTime: locationEmail.modifiedDateTime != null ? locationEmail.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: locationEmail.version,
      comment: locationEmail.comment,
      email: locationEmail.email,
      locationId: locationEmail.locationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const locationEmail = this.createFromForm();
    if (locationEmail.id !== undefined) {
      this.subscribeToSaveResponse(this.locationEmailService.update(locationEmail));
    } else {
      this.subscribeToSaveResponse(this.locationEmailService.create(locationEmail));
    }
  }

  private createFromForm(): ILocationEmail {
    return {
      ...new LocationEmail(),
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
      email: this.editForm.get(['email']).value,
      locationId: this.editForm.get(['locationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocationEmail>>) {
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
