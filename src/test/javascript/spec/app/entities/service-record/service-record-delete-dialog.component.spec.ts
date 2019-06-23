/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRecordDeleteDialogComponent } from 'app/entities/service-record/service-record-delete-dialog.component';
import { ServiceRecordService } from 'app/entities/service-record/service-record.service';

describe('Component Tests', () => {
  describe('ServiceRecord Management Delete Component', () => {
    let comp: ServiceRecordDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceRecordDeleteDialogComponent>;
    let service: ServiceRecordService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRecordDeleteDialogComponent]
      })
        .overrideTemplate(ServiceRecordDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRecordDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRecordService);
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
