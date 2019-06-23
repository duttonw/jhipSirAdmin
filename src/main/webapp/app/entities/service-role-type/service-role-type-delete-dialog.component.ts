import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceRoleType } from 'app/shared/model/service-role-type.model';
import { ServiceRoleTypeService } from './service-role-type.service';

@Component({
  selector: 'jhi-service-role-type-delete-dialog',
  templateUrl: './service-role-type-delete-dialog.component.html'
})
export class ServiceRoleTypeDeleteDialogComponent {
  serviceRoleType: IServiceRoleType;

  constructor(
    protected serviceRoleTypeService: ServiceRoleTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceRoleTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceRoleTypeListModification',
        content: 'Deleted an serviceRoleType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-role-type-delete-popup',
  template: ''
})
export class ServiceRoleTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceRoleType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceRoleTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceRoleType = serviceRoleType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-role-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-role-type', { outlets: { popup: null } }]);
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
