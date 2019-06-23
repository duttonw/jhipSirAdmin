import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgencyType } from 'app/shared/model/agency-type.model';
import { AgencyTypeService } from './agency-type.service';

@Component({
  selector: 'jhi-agency-type-delete-dialog',
  templateUrl: './agency-type-delete-dialog.component.html'
})
export class AgencyTypeDeleteDialogComponent {
  agencyType: IAgencyType;

  constructor(
    protected agencyTypeService: AgencyTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agencyTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agencyTypeListModification',
        content: 'Deleted an agencyType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agency-type-delete-popup',
  template: ''
})
export class AgencyTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agencyType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgencyTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agencyType = agencyType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agency-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agency-type', { outlets: { popup: null } }]);
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
