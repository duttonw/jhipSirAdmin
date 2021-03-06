/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationAddressDeleteDialogComponent } from 'app/entities/location-address/location-address-delete-dialog.component';
import { LocationAddressService } from 'app/entities/location-address/location-address.service';

describe('Component Tests', () => {
  describe('LocationAddress Management Delete Component', () => {
    let comp: LocationAddressDeleteDialogComponent;
    let fixture: ComponentFixture<LocationAddressDeleteDialogComponent>;
    let service: LocationAddressService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationAddressDeleteDialogComponent]
      })
        .overrideTemplate(LocationAddressDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationAddressDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationAddressService);
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
