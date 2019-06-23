import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';
import { ApplicationServiceOverrideTagItemsService } from './application-service-override-tag-items.service';

@Component({
  selector: 'jhi-application-service-override-tag-items-delete-dialog',
  templateUrl: './application-service-override-tag-items-delete-dialog.component.html'
})
export class ApplicationServiceOverrideTagItemsDeleteDialogComponent {
  applicationServiceOverrideTagItems: IApplicationServiceOverrideTagItems;

  constructor(
    protected applicationServiceOverrideTagItemsService: ApplicationServiceOverrideTagItemsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.applicationServiceOverrideTagItemsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationServiceOverrideTagItemsListModification',
        content: 'Deleted an applicationServiceOverrideTagItems'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-application-service-override-tag-items-delete-popup',
  template: ''
})
export class ApplicationServiceOverrideTagItemsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideTagItems }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationServiceOverrideTagItemsDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.applicationServiceOverrideTagItems = applicationServiceOverrideTagItems;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/application-service-override-tag-items', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/application-service-override-tag-items', { outlets: { popup: null } }]);
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
