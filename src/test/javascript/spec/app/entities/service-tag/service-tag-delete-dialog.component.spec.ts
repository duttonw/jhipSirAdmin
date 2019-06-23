/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceTagDeleteDialogComponent } from 'app/entities/service-tag/service-tag-delete-dialog.component';
import { ServiceTagService } from 'app/entities/service-tag/service-tag.service';

describe('Component Tests', () => {
  describe('ServiceTag Management Delete Component', () => {
    let comp: ServiceTagDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceTagDeleteDialogComponent>;
    let service: ServiceTagService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceTagDeleteDialogComponent]
      })
        .overrideTemplate(ServiceTagDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceTagDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceTagService);
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
