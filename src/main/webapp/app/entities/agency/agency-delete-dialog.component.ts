import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgency } from 'app/shared/model/agency.model';
import { AgencyService } from './agency.service';

@Component({
  selector: 'jhi-agency-delete-dialog',
  templateUrl: './agency-delete-dialog.component.html'
})
export class AgencyDeleteDialogComponent {
  agency: IAgency;

  constructor(protected agencyService: AgencyService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.agencyService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'agencyListModification',
        content: 'Deleted an agency'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-agency-delete-popup',
  template: ''
})
export class AgencyDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ agency }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AgencyDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.agency = agency;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/agency', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/agency', { outlets: { popup: null } }]);
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
