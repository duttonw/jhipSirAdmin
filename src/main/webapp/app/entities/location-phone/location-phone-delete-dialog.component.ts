import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocationPhone } from 'app/shared/model/location-phone.model';
import { LocationPhoneService } from './location-phone.service';

@Component({
  selector: 'jhi-location-phone-delete-dialog',
  templateUrl: './location-phone-delete-dialog.component.html'
})
export class LocationPhoneDeleteDialogComponent {
  locationPhone: ILocationPhone;

  constructor(
    protected locationPhoneService: LocationPhoneService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.locationPhoneService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'locationPhoneListModification',
        content: 'Deleted an locationPhone'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-location-phone-delete-popup',
  template: ''
})
export class LocationPhoneDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationPhone }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LocationPhoneDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.locationPhone = locationPhone;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/location-phone', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/location-phone', { outlets: { popup: null } }]);
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
