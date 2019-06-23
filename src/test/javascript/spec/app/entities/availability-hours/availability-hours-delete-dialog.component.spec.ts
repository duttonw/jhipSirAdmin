/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AvailabilityHoursDeleteDialogComponent } from 'app/entities/availability-hours/availability-hours-delete-dialog.component';
import { AvailabilityHoursService } from 'app/entities/availability-hours/availability-hours.service';

describe('Component Tests', () => {
  describe('AvailabilityHours Management Delete Component', () => {
    let comp: AvailabilityHoursDeleteDialogComponent;
    let fixture: ComponentFixture<AvailabilityHoursDeleteDialogComponent>;
    let service: AvailabilityHoursService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AvailabilityHoursDeleteDialogComponent]
      })
        .overrideTemplate(AvailabilityHoursDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvailabilityHoursDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvailabilityHoursService);
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
