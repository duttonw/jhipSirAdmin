import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocationEmail } from 'app/shared/model/location-email.model';
import { LocationEmailService } from './location-email.service';

@Component({
  selector: 'jhi-location-email-delete-dialog',
  templateUrl: './location-email-delete-dialog.component.html'
})
export class LocationEmailDeleteDialogComponent {
  locationEmail: ILocationEmail;

  constructor(
    protected locationEmailService: LocationEmailService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.locationEmailService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'locationEmailListModification',
        content: 'Deleted an locationEmail'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-location-email-delete-popup',
  template: ''
})
export class LocationEmailDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationEmail }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LocationEmailDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.locationEmail = locationEmail;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/location-email', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/location-email', { outlets: { popup: null } }]);
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
