/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryDeleteDialogComponent } from 'app/entities/service-delivery/service-delivery-delete-dialog.component';
import { ServiceDeliveryService } from 'app/entities/service-delivery/service-delivery.service';

describe('Component Tests', () => {
  describe('ServiceDelivery Management Delete Component', () => {
    let comp: ServiceDeliveryDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceDeliveryDeleteDialogComponent>;
    let service: ServiceDeliveryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryDeleteDialogComponent]
      })
        .overrideTemplate(ServiceDeliveryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDeliveryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDeliveryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
