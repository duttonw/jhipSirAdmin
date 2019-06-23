import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';
import { OpeningHoursSpecificationService } from './opening-hours-specification.service';

@Component({
  selector: 'jhi-opening-hours-specification-delete-dialog',
  templateUrl: './opening-hours-specification-delete-dialog.component.html'
})
export class OpeningHoursSpecificationDeleteDialogComponent {
  openingHoursSpecification: IOpeningHoursSpecification;

  constructor(
    protected openingHoursSpecificationService: OpeningHoursSpecificationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.openingHoursSpecificationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'openingHoursSpecificationListModification',
        content: 'Deleted an openingHoursSpecification'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-opening-hours-specification-delete-popup',
  template: ''
})
export class OpeningHoursSpecificationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ openingHoursSpecification }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OpeningHoursSpecificationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.openingHoursSpecification = openingHoursSpecification;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/opening-hours-specification', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/opening-hours-specification', { outlets: { popup: null } }]);
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
