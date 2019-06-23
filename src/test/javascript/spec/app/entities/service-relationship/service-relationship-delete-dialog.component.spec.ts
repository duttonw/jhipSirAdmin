/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRelationshipDeleteDialogComponent } from 'app/entities/service-relationship/service-relationship-delete-dialog.component';
import { ServiceRelationshipService } from 'app/entities/service-relationship/service-relationship.service';

describe('Component Tests', () => {
  describe('ServiceRelationship Management Delete Component', () => {
    let comp: ServiceRelationshipDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceRelationshipDeleteDialogComponent>;
    let service: ServiceRelationshipService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRelationshipDeleteDialogComponent]
      })
        .overrideTemplate(ServiceRelationshipDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRelationshipDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRelationshipService);
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
