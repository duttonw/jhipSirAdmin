/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideTagItemsDeleteDialogComponent } from 'app/entities/application-service-override-tag-items/application-service-override-tag-items-delete-dialog.component';
import { ApplicationServiceOverrideTagItemsService } from 'app/entities/application-service-override-tag-items/application-service-override-tag-items.service';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideTagItems Management Delete Component', () => {
    let comp: ApplicationServiceOverrideTagItemsDeleteDialogComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideTagItemsDeleteDialogComponent>;
    let service: ApplicationServiceOverrideTagItemsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideTagItemsDeleteDialogComponent]
      })
        .overrideTemplate(ApplicationServiceOverrideTagItemsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideTagItemsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideTagItemsService);
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
