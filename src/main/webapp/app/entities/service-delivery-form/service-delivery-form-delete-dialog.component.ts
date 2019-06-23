import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';
import { ServiceDeliveryFormService } from './service-delivery-form.service';

@Component({
  selector: 'jhi-service-delivery-form-delete-dialog',
  templateUrl: './service-delivery-form-delete-dialog.component.html'
})
export class ServiceDeliveryFormDeleteDialogComponent {
  serviceDeliveryForm: IServiceDeliveryForm;

  constructor(
    protected serviceDeliveryFormService: ServiceDeliveryFormService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceDeliveryFormService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceDeliveryFormListModification',
        content: 'Deleted an serviceDeliveryForm'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-delivery-form-delete-popup',
  template: ''
})
export class ServiceDeliveryFormDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDeliveryForm }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceDeliveryFormDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceDeliveryForm = serviceDeliveryForm;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-delivery-form', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-delivery-form', { outlets: { popup: null } }]);
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
