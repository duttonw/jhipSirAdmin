import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';
import { ServiceSupportRoleContextTypeService } from './service-support-role-context-type.service';

@Component({
  selector: 'jhi-service-support-role-context-type-delete-dialog',
  templateUrl: './service-support-role-context-type-delete-dialog.component.html'
})
export class ServiceSupportRoleContextTypeDeleteDialogComponent {
  serviceSupportRoleContextType: IServiceSupportRoleContextType;

  constructor(
    protected serviceSupportRoleContextTypeService: ServiceSupportRoleContextTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceSupportRoleContextTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceSupportRoleContextTypeListModification',
        content: 'Deleted an serviceSupportRoleContextType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-support-role-context-type-delete-popup',
  template: ''
})
export class ServiceSupportRoleContextTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceSupportRoleContextType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceSupportRoleContextTypeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.serviceSupportRoleContextType = serviceSupportRoleContextType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-support-role-context-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-support-role-context-type', { outlets: { popup: null } }]);
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
