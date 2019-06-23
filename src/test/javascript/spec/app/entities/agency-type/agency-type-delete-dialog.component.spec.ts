/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencyTypeDeleteDialogComponent } from 'app/entities/agency-type/agency-type-delete-dialog.component';
import { AgencyTypeService } from 'app/entities/agency-type/agency-type.service';

describe('Component Tests', () => {
  describe('AgencyType Management Delete Component', () => {
    let comp: AgencyTypeDeleteDialogComponent;
    let fixture: ComponentFixture<AgencyTypeDeleteDialogComponent>;
    let service: AgencyTypeService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencyTypeDeleteDialogComponent]
      })
        .overrideTemplate(AgencyTypeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgencyTypeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgencyTypeService);
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
