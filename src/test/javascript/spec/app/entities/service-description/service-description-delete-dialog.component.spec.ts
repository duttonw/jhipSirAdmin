/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDescriptionDeleteDialogComponent } from 'app/entities/service-description/service-description-delete-dialog.component';
import { ServiceDescriptionService } from 'app/entities/service-description/service-description.service';

describe('Component Tests', () => {
  describe('ServiceDescription Management Delete Component', () => {
    let comp: ServiceDescriptionDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceDescriptionDeleteDialogComponent>;
    let service: ServiceDescriptionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDescriptionDeleteDialogComponent]
      })
        .overrideTemplate(ServiceDescriptionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDescriptionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDescriptionService);
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
