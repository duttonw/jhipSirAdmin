import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';
import { ApplicationServiceOverrideTagService } from './application-service-override-tag.service';

@Component({
  selector: 'jhi-application-service-override-tag-delete-dialog',
  templateUrl: './application-service-override-tag-delete-dialog.component.html'
})
export class ApplicationServiceOverrideTagDeleteDialogComponent {
  applicationServiceOverrideTag: IApplicationServiceOverrideTag;

  constructor(
    protected applicationServiceOverrideTagService: ApplicationServiceOverrideTagService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.applicationServiceOverrideTagService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'applicationServiceOverrideTagListModification',
        content: 'Deleted an applicationServiceOverrideTag'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-application-service-override-tag-delete-popup',
  template: ''
})
export class ApplicationServiceOverrideTagDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ applicationServiceOverrideTag }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ApplicationServiceOverrideTagDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.applicationServiceOverrideTag = applicationServiceOverrideTag;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/application-service-override-tag', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/application-service-override-tag', { outlets: { popup: null } }]);
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
