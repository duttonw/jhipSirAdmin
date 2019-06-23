/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideDeleteDialogComponent } from 'app/entities/application-service-override/application-service-override-delete-dialog.component';
import { ApplicationServiceOverrideService } from 'app/entities/application-service-override/application-service-override.service';

describe('Component Tests', () => {
  describe('ApplicationServiceOverride Management Delete Component', () => {
    let comp: ApplicationServiceOverrideDeleteDialogComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideDeleteDialogComponent>;
    let service: ApplicationServiceOverrideService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideDeleteDialogComponent]
      })
        .overrideTemplate(ApplicationServiceOverrideDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideService);
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
