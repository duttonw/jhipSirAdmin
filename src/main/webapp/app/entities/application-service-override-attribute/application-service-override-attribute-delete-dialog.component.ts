import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';
import { ApplicationServiceOverrideAttributeService } from './application-service-override-attribute.service';

@Component({
  selector: 'jhi-application-service-override-attribute-delete-dialog',
  templateUrl: './application-service-override-attribute-delete-dialog.component.html'
})
export class ApplicationServiceOverrideAttributeDeleteDialogComponent {
  applicationServiceOverrideAttribute: IApplicationServiceOverrideAttribute;

  constructor(
    protected applicationServiceOverrideAttributeService: ApplicationServiceOverrideAttributeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.applicationServiceOverrideAttributeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationServiceOverrideAttributeListModification',
        content: 'Deleted an applicationServiceOverrideAttribute'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-application-service-override-attribute-delete-popup',
  template: ''
})
export class ApplicationServiceOverrideAttributeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideAttribute }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationServiceOverrideAttributeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.applicationServiceOverrideAttribute = applicationServiceOverrideAttribute;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/application-service-override-attribute', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/application-service-override-attribute', { outlets: { popup: null } }]);
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
