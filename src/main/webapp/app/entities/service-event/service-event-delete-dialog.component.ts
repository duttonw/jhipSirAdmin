import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceEvent } from 'app/shared/model/service-event.model';
import { ServiceEventService } from './service-event.service';

@Component({
  selector: 'jhi-service-event-delete-dialog',
  templateUrl: './service-event-delete-dialog.component.html'
})
export class ServiceEventDeleteDialogComponent {
  serviceEvent: IServiceEvent;

  constructor(
    protected serviceEventService: ServiceEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceEventService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceEventListModification',
        content: 'Deleted an serviceEvent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-event-delete-popup',
  template: ''
})
export class ServiceEventDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceEvent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceEventDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceEvent = serviceEvent;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-event', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-event', { outlets: { popup: null } }]);
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
