import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';
import { AgencySupportRoleContextTypeService } from './agency-support-role-context-type.service';

@Component({
  selector: 'jhi-agency-support-role-context-type-delete-dialog',
  templateUrl: './agency-support-role-context-type-delete-dialog.component.html'
})
export class AgencySupportRoleContextTypeDeleteDialogComponent {
  agencySupportRoleContextType: IAgencySupportRoleContextType;

  constructor(
    protected agencySupportRoleContextTypeService: AgencySupportRoleContextTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agencySupportRoleContextTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agencySupportRoleContextTypeListModification',
        content: 'Deleted an agencySupportRoleContextType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agency-support-role-context-type-delete-popup',
  template: ''
})
export class AgencySupportRoleContextTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agencySupportRoleContextType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgencySupportRoleContextTypeDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.agencySupportRoleContextType = agencySupportRoleContextType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agency-support-role-context-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agency-support-role-context-type', { outlets: { popup: null } }]);
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
