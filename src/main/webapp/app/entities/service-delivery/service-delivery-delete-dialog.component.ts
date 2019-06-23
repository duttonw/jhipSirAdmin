import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceDelivery } from 'app/shared/model/service-delivery.model';
import { ServiceDeliveryService } from './service-delivery.service';

@Component({
  selector: 'jhi-service-delivery-delete-dialog',
  templateUrl: './service-delivery-delete-dialog.component.html'
})
export class ServiceDeliveryDeleteDialogComponent {
  serviceDelivery: IServiceDelivery;

  constructor(
    protected serviceDeliveryService: ServiceDeliveryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceDeliveryService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceDeliveryListModification',
        content: 'Deleted an serviceDelivery'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-delivery-delete-popup',
  template: ''
})
export class ServiceDeliveryDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDelivery }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceDeliveryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceDelivery = serviceDelivery;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-delivery', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-delivery', { outlets: { popup: null } }]);
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
