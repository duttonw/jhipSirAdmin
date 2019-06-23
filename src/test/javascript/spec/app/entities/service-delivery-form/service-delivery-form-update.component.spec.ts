/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryFormUpdateComponent } from 'app/entities/service-delivery-form/service-delivery-form-update.component';
import { ServiceDeliveryFormService } from 'app/entities/service-delivery-form/service-delivery-form.service';
import { ServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';

describe('Component Tests', () => {
  describe('ServiceDeliveryForm Management Update Component', () => {
    let comp: ServiceDeliveryFormUpdateComponent;
    let fixture: ComponentFixture<ServiceDeliveryFormUpdateComponent>;
    let service: ServiceDeliveryFormService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryFormUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceDeliveryFormUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceDeliveryFormUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDeliveryFormService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceDeliveryForm(123);
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
        const entity = new ServiceDeliveryForm();
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
