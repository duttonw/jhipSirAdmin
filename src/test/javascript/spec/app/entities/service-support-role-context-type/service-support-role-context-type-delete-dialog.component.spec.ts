/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceSupportRoleContextTypeDeleteDialogComponent } from 'app/entities/service-support-role-context-type/service-support-role-context-type-delete-dialog.component';
import { ServiceSupportRoleContextTypeService } from 'app/entities/service-support-role-context-type/service-support-role-context-type.service';

describe('Component Tests', () => {
  describe('ServiceSupportRoleContextType Management Delete Component', () => {
    let comp: ServiceSupportRoleContextTypeDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceSupportRoleContextTypeDeleteDialogComponent>;
    let service: ServiceSupportRoleContextTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceSupportRoleContextTypeDeleteDialogComponent]
      })
        .overrideTemplate(ServiceSupportRoleContextTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceSupportRoleContextTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceSupportRoleContextTypeService);
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
