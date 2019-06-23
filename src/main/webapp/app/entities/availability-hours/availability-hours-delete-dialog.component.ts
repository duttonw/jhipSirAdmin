import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvailabilityHours } from 'app/shared/model/availability-hours.model';
import { AvailabilityHoursService } from './availability-hours.service';

@Component({
  selector: 'jhi-availability-hours-delete-dialog',
  templateUrl: './availability-hours-delete-dialog.component.html'
})
export class AvailabilityHoursDeleteDialogComponent {
  availabilityHours: IAvailabilityHours;

  constructor(
    protected availabilityHoursService: AvailabilityHoursService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.availabilityHoursService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'availabilityHoursListModification',
        content: 'Deleted an availabilityHours'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-availability-hours-delete-popup',
  template: ''
})
export class AvailabilityHoursDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ availabilityHours }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AvailabilityHoursDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.availabilityHours = availabilityHours;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/availability-hours', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/availability-hours', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
