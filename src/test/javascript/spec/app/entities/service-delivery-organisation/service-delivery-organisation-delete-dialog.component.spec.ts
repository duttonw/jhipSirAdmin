/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryOrganisationDeleteDialogComponent } from 'app/entities/service-delivery-organisation/service-delivery-organisation-delete-dialog.component';
import { ServiceDeliveryOrganisationService } from 'app/entities/service-delivery-organisation/service-delivery-organisation.service';

describe('Component Tests', () => {
  describe('ServiceDeliveryOrganisation Management Delete Component', () => {
    let comp: ServiceDeliveryOrganisationDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceDeliveryOrganisationDeleteDialogComponent>;
    let service: ServiceDeliveryOrganisationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryOrganisationDeleteDialogComponent]
      })
        .overrideTemplate(ServiceDeliveryOrganisationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDeliveryOrganisationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDeliveryOrganisationService);
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
