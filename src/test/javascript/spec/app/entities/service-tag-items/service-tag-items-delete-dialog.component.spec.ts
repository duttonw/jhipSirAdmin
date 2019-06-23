/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceTagItemsDeleteDialogComponent } from 'app/entities/service-tag-items/service-tag-items-delete-dialog.component';
import { ServiceTagItemsService } from 'app/entities/service-tag-items/service-tag-items.service';

describe('Component Tests', () => {
  describe('ServiceTagItems Management Delete Component', () => {
    let comp: ServiceTagItemsDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceTagItemsDeleteDialogComponent>;
    let service: ServiceTagItemsService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceTagItemsDeleteDialogComponent]
      })
        .overrideTemplate(ServiceTagItemsDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceTagItemsDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceTagItemsService);
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
