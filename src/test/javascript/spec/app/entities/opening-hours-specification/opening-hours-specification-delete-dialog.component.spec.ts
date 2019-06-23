/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { OpeningHoursSpecificationDeleteDialogComponent } from 'app/entities/opening-hours-specification/opening-hours-specification-delete-dialog.component';
import { OpeningHoursSpecificationService } from 'app/entities/opening-hours-specification/opening-hours-specification.service';

describe('Component Tests', () => {
  describe('OpeningHoursSpecification Management Delete Component', () => {
    let comp: OpeningHoursSpecificationDeleteDialogComponent;
    let fixture: ComponentFixture<OpeningHoursSpecificationDeleteDialogComponent>;
    let service: OpeningHoursSpecificationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [OpeningHoursSpecificationDeleteDialogComponent]
      })
        .overrideTemplate(OpeningHoursSpecificationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpeningHoursSpecificationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpeningHoursSpecificationService);
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
