/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryFormDeleteDialogComponent } from 'app/entities/service-delivery-form/service-delivery-form-delete-dialog.component';
import { ServiceDeliveryFormService } from 'app/entities/service-delivery-form/service-delivery-form.service';

describe('Component Tests', () => {
  describe('ServiceDeliveryForm Management Delete Component', () => {
    let comp: ServiceDeliveryFormDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceDeliveryFormDeleteDialogComponent>;
    let service: ServiceDeliveryFormService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryFormDeleteDialogComponent]
      })
        .overrideTemplate(ServiceDeliveryFormDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDeliveryFormDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDeliveryFormService);
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
