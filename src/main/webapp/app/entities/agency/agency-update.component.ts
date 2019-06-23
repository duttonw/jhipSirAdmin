import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAgency, Agency } from 'app/shared/model/agency.model';
import { AgencyService } from './agency.service';
import { IAgencyType } from 'app/shared/model/agency-type.model';
import { AgencyTypeService } from 'app/entities/agency-type';

@Component({
  selector: 'jhi-agency-update',
  templateUrl: './agency-update.component.html'
})
export class AgencyUpdateComponent implements OnInit {
  isSaving: boolean;

  agencytypes: IAgencyType[];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.maxLength(255)]],
    createdDateTime: [],
    modifiedBy: [null, [Validators.maxLength(255)]],
    modifiedDateTime: [],
    version: [],
    agencyCode: [null, [Validators.maxLength(255)]],
    agencyName: [null, [Validators.maxLength(255)]],
    agencyUrl: [null, [Validators.maxLength(255)]],
    agencyTypeId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected agencyService: AgencyService,
    protected agencyTypeService: AgencyTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ agency }) => {
      this.updateForm(agency);
    });
    this.agencyTypeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAgencyType[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAgencyType[]>) => response.body)
      )
      .subscribe((res: IAgencyType[]) => (this.agencytypes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(agency: IAgency) {
    this.editForm.patchValue({
      id: agency.id,
      createdBy: agency.createdBy,
      createdDateTime: agency.createdDateTime != null ? agency.createdDateTime.format(DATE_TIME_FORMAT) : null,
      modifiedBy: agency.modifiedBy,
      modifiedDateTime: agency.modifiedDateTime != null ? agency.modifiedDateTime.format(DATE_TIME_FORMAT) : null,
      version: agency.version,
      agencyCode: agency.agencyCode,
      agencyName: agency.agencyName,
      agencyUrl: agency.agencyUrl,
      agencyTypeId: agency.agencyTypeId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const agency = this.createFromForm();
    if (agency.id !== undefined) {
      this.subscribeToSaveResponse(this.agencyService.update(agency));
    } else {
      this.subscribeToSaveResponse(this.agencyService.create(agency));
    }
  }

  private createFromForm(): IAgency {
    return {
      ...new Agency(),
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
      agencyCode: this.editForm.get(['agencyCode']).value,
      agencyName: this.editForm.get(['agencyName']).value,
      agencyUrl: this.editForm.get(['agencyUrl']).value,
      agencyTypeId: this.editForm.get(['agencyTypeId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgency>>) {
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

  trackAgencyTypeById(index: number, item: IAgencyType) {
    return item.id;
  }
}
