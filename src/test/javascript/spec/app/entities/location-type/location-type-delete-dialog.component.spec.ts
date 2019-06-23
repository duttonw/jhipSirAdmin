/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationTypeDeleteDialogComponent } from 'app/entities/location-type/location-type-delete-dialog.component';
import { LocationTypeService } from 'app/entities/location-type/location-type.service';

describe('Component Tests', () => {
  describe('LocationType Management Delete Component', () => {
    let comp: LocationTypeDeleteDialogComponent;
    let fixture: ComponentFixture<LocationTypeDeleteDialogComponent>;
    let service: LocationTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationTypeDeleteDialogComponent]
      })
        .overrideTemplate(LocationTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationTypeService);
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
