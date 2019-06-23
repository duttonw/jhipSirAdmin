import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgencySupportRole } from 'app/shared/model/agency-support-role.model';
import { AgencySupportRoleService } from './agency-support-role.service';

@Component({
  selector: 'jhi-agency-support-role-delete-dialog',
  templateUrl: './agency-support-role-delete-dialog.component.html'
})
export class AgencySupportRoleDeleteDialogComponent {
  agencySupportRole: IAgencySupportRole;

  constructor(
    protected agencySupportRoleService: AgencySupportRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agencySupportRoleService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agencySupportRoleListModification',
        content: 'Deleted an agencySupportRole'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agency-support-role-delete-popup',
  template: ''
})
export class AgencySupportRoleDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agencySupportRole }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgencySupportRoleDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agencySupportRole = agencySupportRole;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agency-support-role', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agency-support-role', { outlets: { popup: null } }]);
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
