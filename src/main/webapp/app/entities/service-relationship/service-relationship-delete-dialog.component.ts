import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceRelationship } from 'app/shared/model/service-relationship.model';
import { ServiceRelationshipService } from './service-relationship.service';

@Component({
  selector: 'jhi-service-relationship-delete-dialog',
  templateUrl: './service-relationship-delete-dialog.component.html'
})
export class ServiceRelationshipDeleteDialogComponent {
  serviceRelationship: IServiceRelationship;

  constructor(
    protected serviceRelationshipService: ServiceRelationshipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceRelationshipService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceRelationshipListModification',
        content: 'Deleted an serviceRelationship'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-relationship-delete-popup',
  template: ''
})
export class ServiceRelationshipDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRelationship }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceRelationshipDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceRelationship = serviceRelationship;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-relationship', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-relationship', { outlets: { popup: null } }]);
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
