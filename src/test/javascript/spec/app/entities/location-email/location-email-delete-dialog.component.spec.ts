/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationEmailDeleteDialogComponent } from 'app/entities/location-email/location-email-delete-dialog.component';
import { LocationEmailService } from 'app/entities/location-email/location-email.service';

describe('Component Tests', () => {
  describe('LocationEmail Management Delete Component', () => {
    let comp: LocationEmailDeleteDialogComponent;
    let fixture: ComponentFixture<LocationEmailDeleteDialogComponent>;
    let service: LocationEmailService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationEmailDeleteDialogComponent]
      })
        .overrideTemplate(LocationEmailDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationEmailDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationEmailService);
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
