import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocationType } from 'app/shared/model/location-type.model';
import { LocationTypeService } from './location-type.service';

@Component({
  selector: 'jhi-location-type-delete-dialog',
  templateUrl: './location-type-delete-dialog.component.html'
})
export class LocationTypeDeleteDialogComponent {
  locationType: ILocationType;

  constructor(
    protected locationTypeService: LocationTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.locationTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'locationTypeListModification',
        content: 'Deleted an locationType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-location-type-delete-popup',
  template: ''
})
export class LocationTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ locationType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(LocationTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.locationType = locationType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/location-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/location-type', { outlets: { popup: null } }]);
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
