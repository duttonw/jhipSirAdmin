/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryOrganisationUpdateComponent } from 'app/entities/service-delivery-organisation/service-delivery-organisation-update.component';
import { ServiceDeliveryOrganisationService } from 'app/entities/service-delivery-organisation/service-delivery-organisation.service';
import { ServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';

describe('Component Tests', () => {
  describe('ServiceDeliveryOrganisation Management Update Component', () => {
    let comp: ServiceDeliveryOrganisationUpdateComponent;
    let fixture: ComponentFixture<ServiceDeliveryOrganisationUpdateComponent>;
    let service: ServiceDeliveryOrganisationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryOrganisationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceDeliveryOrganisationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceDeliveryOrganisationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDeliveryOrganisationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceDeliveryOrganisation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceDeliveryOrganisation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
