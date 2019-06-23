import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceName } from 'app/shared/model/service-name.model';
import { ServiceNameService } from './service-name.service';

@Component({
  selector: 'jhi-service-name-delete-dialog',
  templateUrl: './service-name-delete-dialog.component.html'
})
export class ServiceNameDeleteDialogComponent {
  serviceName: IServiceName;

  constructor(
    protected serviceNameService: ServiceNameService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceNameService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceNameListModification',
        content: 'Deleted an serviceName'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-name-delete-popup',
  template: ''
})
export class ServiceNameDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceName }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceNameDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceName = serviceName;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-name', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-name', { outlets: { popup: null } }]);
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
