import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOpeningHoursSpecification, OpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';
import { OpeningHoursSpecificationService } from './opening-hours-specification.service';
import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';
import { AvailabilityHoursService } from 'app/entities/availability-hours';

@Component({
  selector: 'jhi-opening-hours-specification-update',
  templateUrl: './opening-hours-specification-update.component.html'
})
export class OpeningHoursSpecificationUpdateComponent implements OnInit {
  isSaving: boolean;

  availabilityhours: IAvailabilityHours[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    closes: [null, [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]d)|(2[0-3])):([0-5]d):([0-5]d)$')]],
    dayOfWeek: [null, [Validators.maxLength(255)]],
    opens: [null, [Validators.minLength(8), Validators.maxLength(8), Validators.pattern('^(([0-1]d)|(2[0-3])):([0-5]d):([0-5]d)$')]],
    validFrom: [],
    validTo: [],
    availabilityHoursId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected openingHoursSpecificationService: OpeningHoursSpecificationService,
    protected availabilityHoursService: AvailabilityHoursService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ openingHoursSpecification }) => {
      this.updateForm(openingHoursSpecification);
    });
    this.availabilityHoursService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAvailabilityHours[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAvailabilityHours[]>) => response.body)
      )
      .subscribe((res: IAvailabilityHours[]) => (this.availabilityhours = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(openingHoursSpecification: IOpeningHoursSpecification) {
    this.editForm.patchValue({
      id: openingHoursSpecification.id,
      createdBy: openingHoursSpecification.createdBy,
      createdDateTime:
        openingHoursSpecification.createdDateTime != null ? openingHoursSpecification.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: openingHoursSpecification.modifiedBy,
      modifiedDateTime:
        openingHoursSpecification.modifiedDateTime != null ? openingHoursSpecification.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: openingHoursSpecification.version,
      closes: openingHoursSpecification.closes,
      dayOfWeek: openingHoursSpecification.dayOfWeek,
      opens: openingHoursSpecification.opens,
      validFrom: openingHoursSpecification.validFrom != null ? openingHoursSpecification.validFrom.format(DATE_TIME_FORMAT) : null,
      validTo: openingHoursSpecification.validTo != null ? openingHoursSpecification.validTo.format(DATE_TIME_FORMAT) : null,
      availabilityHoursId: openingHoursSpecification.availabilityHoursId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const openingHoursSpecification = this.createFromForm();
    if (openingHoursSpecification.id !== undefined) {
      this.subscribeToSaveResponse(this.openingHoursSpecificationService.update(openingHoursSpecification));
    } else {
      this.subscribeToSaveResponse(this.openingHoursSpecificationService.create(openingHoursSpecification));
    }
  }

  private createFromForm(): IOpeningHoursSpecification {
    return {
      ...new OpeningHoursSpecification(),
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
      closes: this.editForm.get(['closes']).value,
      dayOfWeek: this.editForm.get(['dayOfWeek']).value,
      opens: this.editForm.get(['opens']).value,
      validFrom:
        this.editForm.get(['validFrom']).value != null ? moment(this.editForm.get(['validFrom']).value, DATE_TIME_FORMAT) : undefined,
      validTo: this.editForm.get(['validTo']).value != null ? moment(this.editForm.get(['validTo']).value, DATE_TIME_FORMAT) : undefined,
      availabilityHoursId: this.editForm.get(['availabilityHoursId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpeningHoursSpecification>>) {
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
}
