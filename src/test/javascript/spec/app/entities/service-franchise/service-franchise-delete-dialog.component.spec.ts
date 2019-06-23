/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceFranchiseDeleteDialogComponent } from 'app/entities/service-franchise/service-franchise-delete-dialog.component';
import { ServiceFranchiseService } from 'app/entities/service-franchise/service-franchise.service';

describe('Component Tests', () => {
  describe('ServiceFranchise Management Delete Component', () => {
    let comp: ServiceFranchiseDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceFranchiseDeleteDialogComponent>;
    let service: ServiceFranchiseService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceFranchiseDeleteDialogComponent]
      })
        .overrideTemplate(ServiceFranchiseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceFranchiseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceFranchiseService);
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
