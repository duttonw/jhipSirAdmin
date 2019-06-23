/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideAttributeDeleteDialogComponent } from 'app/entities/application-service-override-attribute/application-service-override-attribute-delete-dialog.component';
import { ApplicationServiceOverrideAttributeService } from 'app/entities/application-service-override-attribute/application-service-override-attribute.service';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideAttribute Management Delete Component', () => {
    let comp: ApplicationServiceOverrideAttributeDeleteDialogComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideAttributeDeleteDialogComponent>;
    let service: ApplicationServiceOverrideAttributeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideAttributeDeleteDialogComponent]
      })
        .overrideTemplate(ApplicationServiceOverrideAttributeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideAttributeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideAttributeService);
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
