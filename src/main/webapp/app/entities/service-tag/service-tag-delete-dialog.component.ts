import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceTag } from 'app/shared/model/service-tag.model';
import { ServiceTagService } from './service-tag.service';

@Component({
  selector: 'jhi-service-tag-delete-dialog',
  templateUrl: './service-tag-delete-dialog.component.html'
})
export class ServiceTagDeleteDialogComponent {
  serviceTag: IServiceTag;

  constructor(
    protected serviceTagService: ServiceTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceTagService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceTagListModification',
        content: 'Deleted an serviceTag'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-service-tag-delete-popup',
  template: ''
})
export class ServiceTagDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceTag }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceTagDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceTag = serviceTag;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-tag', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-tag', { outlets: { popup: null } }]);
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
