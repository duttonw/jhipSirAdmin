/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRoleTypeDeleteDialogComponent } from 'app/entities/service-role-type/service-role-type-delete-dialog.component';
import { ServiceRoleTypeService } from 'app/entities/service-role-type/service-role-type.service';

describe('Component Tests', () => {
  describe('ServiceRoleType Management Delete Component', () => {
    let comp: ServiceRoleTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceRoleTypeDeleteDialogComponent>;
    let service: ServiceRoleTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRoleTypeDeleteDialogComponent]
      })
        .overrideTemplate(ServiceRoleTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRoleTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRoleTypeService);
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
