import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';
import { ServiceDeliveryOrganisationService } from './service-delivery-organisation.service';

@Component({
  selector: 'jhi-service-delivery-organisation-delete-dialog',
  templateUrl: './service-delivery-organisation-delete-dialog.component.html'
})
export class ServiceDeliveryOrganisationDeleteDialogComponent {
  serviceDeliveryOrganisation: IServiceDeliveryOrganisation;

  constructor(
    protected serviceDeliveryOrganisationService: ServiceDeliveryOrganisationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceDeliveryOrganisationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceDeliveryOrganisationListModification',
        content: 'Deleted an serviceDeliveryOrganisation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-delivery-organisation-delete-popup',
  template: ''
})
export class ServiceDeliveryOrganisationDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDeliveryOrganisation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceDeliveryOrganisationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceDeliveryOrganisation = serviceDeliveryOrganisation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-delivery-organisation', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-delivery-organisation', { outlets: { popup: null } }]);
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
