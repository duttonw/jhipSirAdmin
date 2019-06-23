import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceEventType } from 'app/shared/model/service-event-type.model';
import { ServiceEventTypeService } from './service-event-type.service';

@Component({
  selector: 'jhi-service-event-type-delete-dialog',
  templateUrl: './service-event-type-delete-dialog.component.html'
})
export class ServiceEventTypeDeleteDialogComponent {
  serviceEventType: IServiceEventType;

  constructor(
    protected serviceEventTypeService: ServiceEventTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceEventTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceEventTypeListModification',
        content: 'Deleted an serviceEventType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-event-type-delete-popup',
  template: ''
})
export class ServiceEventTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceEventType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceEventTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceEventType = serviceEventType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-event-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-event-type', { outlets: { popup: null } }]);
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
