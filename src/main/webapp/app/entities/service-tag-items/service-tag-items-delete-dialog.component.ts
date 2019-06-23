import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceTagItems } from 'app/shared/model/service-tag-items.model';
import { ServiceTagItemsService } from './service-tag-items.service';

@Component({
  selector: 'jhi-service-tag-items-delete-dialog',
  templateUrl: './service-tag-items-delete-dialog.component.html'
})
export class ServiceTagItemsDeleteDialogComponent {
  serviceTagItems: IServiceTagItems;

  constructor(
    protected serviceTagItemsService: ServiceTagItemsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceTagItemsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceTagItemsListModification',
        content: 'Deleted an serviceTagItems'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-tag-items-delete-popup',
  template: ''
})
export class ServiceTagItemsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceTagItems }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceTagItemsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceTagItems = serviceTagItems;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-tag-items', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-tag-items', { outlets: { popup: null } }]);
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
