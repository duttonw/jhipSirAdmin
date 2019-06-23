import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationServiceOverride } from 'app/shared/model/application-service-override.model';
import { ApplicationServiceOverrideService } from './application-service-override.service';

@Component({
  selector: 'jhi-application-service-override-delete-dialog',
  templateUrl: './application-service-override-delete-dialog.component.html'
})
export class ApplicationServiceOverrideDeleteDialogComponent {
  applicationServiceOverride: IApplicationServiceOverride;

  constructor(
    protected applicationServiceOverrideService: ApplicationServiceOverrideService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.applicationServiceOverrideService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationServiceOverrideListModification',
        content: 'Deleted an applicationServiceOverride'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-application-service-override-delete-popup',
  template: ''
})
export class ApplicationServiceOverrideDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverride }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationServiceOverrideDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.applicationServiceOverride = applicationServiceOverride;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/application-service-override', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/application-service-override', { outlets: { popup: null } }]);
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
