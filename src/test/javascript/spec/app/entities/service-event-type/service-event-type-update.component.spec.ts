/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEventTypeUpdateComponent } from 'app/entities/service-event-type/service-event-type-update.component';
import { ServiceEventTypeService } from 'app/entities/service-event-type/service-event-type.service';
import { ServiceEventType } from 'app/shared/model/service-event-type.model';

describe('Component Tests', () => {
  describe('ServiceEventType Management Update Component', () => {
    let comp: ServiceEventTypeUpdateComponent;
    let fixture: ComponentFixture<ServiceEventTypeUpdateComponent>;
    let service: ServiceEventTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEventTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceEventTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceEventTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceEventTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceEventType(123);
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
        const entity = new ServiceEventType();
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
