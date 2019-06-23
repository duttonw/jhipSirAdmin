/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideTagDeleteDialogComponent } from 'app/entities/application-service-override-tag/application-service-override-tag-delete-dialog.component';
import { ApplicationServiceOverrideTagService } from 'app/entities/application-service-override-tag/application-service-override-tag.service';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideTag Management Delete Component', () => {
    let comp: ApplicationServiceOverrideTagDeleteDialogComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideTagDeleteDialogComponent>;
    let service: ApplicationServiceOverrideTagService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideTagDeleteDialogComponent]
      })
        .overrideTemplate(ApplicationServiceOverrideTagDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideTagDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideTagService);
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
