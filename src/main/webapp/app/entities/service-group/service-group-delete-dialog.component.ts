import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceGroup } from 'app/shared/model/service-group.model';
import { ServiceGroupService } from './service-group.service';

@Component({
  selector: 'jhi-service-group-delete-dialog',
  templateUrl: './service-group-delete-dialog.component.html'
})
export class ServiceGroupDeleteDialogComponent {
  serviceGroup: IServiceGroup;

  constructor(
    protected serviceGroupService: ServiceGroupService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceGroupService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceGroupListModification',
        content: 'Deleted an serviceGroup'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-group-delete-popup',
  template: ''
})
export class ServiceGroupDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceGroup }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceGroupDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceGroup = serviceGroup;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-group', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-group', { outlets: { popup: null } }]);
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
