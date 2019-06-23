import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocationAddress } from 'app/shared/model/location-address.model';
import { LocationAddressService } from './location-address.service';

@Component({
  selector: 'jhi-location-address-delete-dialog',
  templateUrl: './location-address-delete-dialog.component.html'
})
export class LocationAddressDeleteDialogComponent {
  locationAddress: ILocationAddress;

  constructor(
    protected locationAddressService: LocationAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.locationAddressService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'locationAddressListModification',
        content: 'Deleted an locationAddress'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-location-address-delete-popup',
  template: ''
})
export class LocationAddressDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationAddress }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LocationAddressDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.locationAddress = locationAddress;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/location-address', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/location-address', { outlets: { popup: null } }]);
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
