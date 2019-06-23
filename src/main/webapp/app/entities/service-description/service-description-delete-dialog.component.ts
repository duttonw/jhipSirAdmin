import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceDescription } from 'app/shared/model/service-description.model';
import { ServiceDescriptionService } from './service-description.service';

@Component({
  selector: 'jhi-service-description-delete-dialog',
  templateUrl: './service-description-delete-dialog.component.html'
})
export class ServiceDescriptionDeleteDialogComponent {
  serviceDescription: IServiceDescription;

  constructor(
    protected serviceDescriptionService: ServiceDescriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceDescriptionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceDescriptionListModification',
        content: 'Deleted an serviceDescription'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-description-delete-popup',
  template: ''
})
export class ServiceDescriptionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceDescription }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceDescriptionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceDescription = serviceDescription;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-description', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-description', { outlets: { popup: null } }]);
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
