import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryChannel } from 'app/shared/model/delivery-channel.model';
import { DeliveryChannelService } from './delivery-channel.service';

@Component({
  selector: 'jhi-delivery-channel-delete-dialog',
  templateUrl: './delivery-channel-delete-dialog.component.html'
})
export class DeliveryChannelDeleteDialogComponent {
  deliveryChannel: IDeliveryChannel;

  constructor(
    protected deliveryChannelService: DeliveryChannelService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.deliveryChannelService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'deliveryChannelListModification',
        content: 'Deleted an deliveryChannel'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-delivery-channel-delete-popup',
  template: ''
})
export class DeliveryChannelDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ deliveryChannel }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DeliveryChannelDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.deliveryChannel = deliveryChannel;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/delivery-channel', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/delivery-channel', { outlets: { popup: null } }]);
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
