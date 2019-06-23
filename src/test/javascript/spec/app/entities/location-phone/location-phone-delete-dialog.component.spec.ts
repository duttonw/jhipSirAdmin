/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationPhoneDeleteDialogComponent } from 'app/entities/location-phone/location-phone-delete-dialog.component';
import { LocationPhoneService } from 'app/entities/location-phone/location-phone.service';

describe('Component Tests', () => {
  describe('LocationPhone Management Delete Component', () => {
    let comp: LocationPhoneDeleteDialogComponent;
    let fixture: ComponentFixture<LocationPhoneDeleteDialogComponent>;
    let service: LocationPhoneService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationPhoneDeleteDialogComponent]
      })
        .overrideTemplate(LocationPhoneDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationPhoneDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationPhoneService);
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
