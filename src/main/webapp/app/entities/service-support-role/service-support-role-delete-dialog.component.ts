import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceSupportRole } from 'app/shared/model/service-support-role.model';
import { ServiceSupportRoleService } from './service-support-role.service';

@Component({
  selector: 'jhi-service-support-role-delete-dialog',
  templateUrl: './service-support-role-delete-dialog.component.html'
})
export class ServiceSupportRoleDeleteDialogComponent {
  serviceSupportRole: IServiceSupportRole;

  constructor(
    protected serviceSupportRoleService: ServiceSupportRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceSupportRoleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceSupportRoleListModification',
        content: 'Deleted an serviceSupportRole'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-support-role-delete-popup',
  template: ''
})
export class ServiceSupportRoleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceSupportRole }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceSupportRoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceSupportRole = serviceSupportRole;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-support-role', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-support-role', { outlets: { popup: null } }]);
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
