/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryUpdateComponent } from 'app/entities/service-delivery/service-delivery-update.component';
import { ServiceDeliveryService } from 'app/entities/service-delivery/service-delivery.service';
import { ServiceDelivery } from 'app/shared/model/service-delivery.model';

describe('Component Tests', () => {
  describe('ServiceDelivery Management Update Component', () => {
    let comp: ServiceDeliveryUpdateComponent;
    let fixture: ComponentFixture<ServiceDeliveryUpdateComponent>;
    let service: ServiceDeliveryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceDeliveryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceDeliveryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceDeliveryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceDelivery(123);
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
        const entity = new ServiceDelivery();
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
