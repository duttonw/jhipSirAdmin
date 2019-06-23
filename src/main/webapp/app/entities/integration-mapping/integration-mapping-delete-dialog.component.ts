import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIntegrationMapping } from 'app/shared/model/integration-mapping.model';
import { IntegrationMappingService } from './integration-mapping.service';

@Component({
  selector: 'jhi-integration-mapping-delete-dialog',
  templateUrl: './integration-mapping-delete-dialog.component.html'
})
export class IntegrationMappingDeleteDialogComponent {
  integrationMapping: IIntegrationMapping;

  constructor(
    protected integrationMappingService: IntegrationMappingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.integrationMappingService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'integrationMappingListModification',
        content: 'Deleted an integrationMapping'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-integration-mapping-delete-popup',
  template: ''
})
export class IntegrationMappingDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ integrationMapping }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(IntegrationMappingDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.integrationMapping = integrationMapping;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/integration-mapping', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/integration-mapping', { outlets: { popup: null } }]);
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
