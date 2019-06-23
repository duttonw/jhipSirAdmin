import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceFranchise } from 'app/shared/model/service-franchise.model';
import { ServiceFranchiseService } from './service-franchise.service';

@Component({
  selector: 'jhi-service-franchise-delete-dialog',
  templateUrl: './service-franchise-delete-dialog.component.html'
})
export class ServiceFranchiseDeleteDialogComponent {
  serviceFranchise: IServiceFranchise;

  constructor(
    protected serviceFranchiseService: ServiceFranchiseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceFranchiseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceFranchiseListModification',
        content: 'Deleted an serviceFranchise'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-franchise-delete-popup',
  template: ''
})
export class ServiceFranchiseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceFranchise }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceFranchiseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceFranchise = serviceFranchise;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-franchise', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-franchise', { outlets: { popup: null } }]);
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
