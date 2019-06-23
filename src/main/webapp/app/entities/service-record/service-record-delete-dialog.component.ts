import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceRecord } from 'app/shared/model/service-record.model';
import { ServiceRecordService } from './service-record.service';

@Component({
  selector: 'jhi-service-record-delete-dialog',
  templateUrl: './service-record-delete-dialog.component.html'
})
export class ServiceRecordDeleteDialogComponent {
  serviceRecord: IServiceRecord;

  constructor(
    protected serviceRecordService: ServiceRecordService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceRecordService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceRecordListModification',
        content: 'Deleted an serviceRecord'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-record-delete-popup',
  template: ''
})
export class ServiceRecordDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRecord }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceRecordDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceRecord = serviceRecord;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-record', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-record', { outlets: { popup: null } }]);
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
