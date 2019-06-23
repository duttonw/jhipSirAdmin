/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEventDeleteDialogComponent } from 'app/entities/service-event/service-event-delete-dialog.component';
import { ServiceEventService } from 'app/entities/service-event/service-event.service';

describe('Component Tests', () => {
  describe('ServiceEvent Management Delete Component', () => {
    let comp: ServiceEventDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceEventDeleteDialogComponent>;
    let service: ServiceEventService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEventDeleteDialogComponent]
      })
        .overrideTemplate(ServiceEventDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceEventDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceEventService);
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
