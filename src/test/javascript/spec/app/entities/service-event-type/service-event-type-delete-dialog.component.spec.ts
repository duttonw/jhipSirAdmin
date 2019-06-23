/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEventTypeDeleteDialogComponent } from 'app/entities/service-event-type/service-event-type-delete-dialog.component';
import { ServiceEventTypeService } from 'app/entities/service-event-type/service-event-type.service';

describe('Component Tests', () => {
  describe('ServiceEventType Management Delete Component', () => {
    let comp: ServiceEventTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceEventTypeDeleteDialogComponent>;
    let service: ServiceEventTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEventTypeDeleteDialogComponent]
      })
        .overrideTemplate(ServiceEventTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceEventTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceEventTypeService);
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
