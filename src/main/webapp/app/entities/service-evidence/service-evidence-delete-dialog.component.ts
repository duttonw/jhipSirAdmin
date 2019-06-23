import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceEvidence } from 'app/shared/model/service-evidence.model';
import { ServiceEvidenceService } from './service-evidence.service';

@Component({
  selector: 'jhi-service-evidence-delete-dialog',
  templateUrl: './service-evidence-delete-dialog.component.html'
})
export class ServiceEvidenceDeleteDialogComponent {
  serviceEvidence: IServiceEvidence;

  constructor(
    protected serviceEvidenceService: ServiceEvidenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceEvidenceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceEvidenceListModification',
        content: 'Deleted an serviceEvidence'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-evidence-delete-popup',
  template: ''
})
export class ServiceEvidenceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceEvidence }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceEvidenceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceEvidence = serviceEvidence;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-evidence', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-evidence', { outlets: { popup: null } }]);
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
