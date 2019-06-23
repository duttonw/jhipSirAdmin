/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEvidenceDeleteDialogComponent } from 'app/entities/service-evidence/service-evidence-delete-dialog.component';
import { ServiceEvidenceService } from 'app/entities/service-evidence/service-evidence.service';

describe('Component Tests', () => {
  describe('ServiceEvidence Management Delete Component', () => {
    let comp: ServiceEvidenceDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceEvidenceDeleteDialogComponent>;
    let service: ServiceEvidenceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEvidenceDeleteDialogComponent]
      })
        .overrideTemplate(ServiceEvidenceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceEvidenceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceEvidenceService);
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
