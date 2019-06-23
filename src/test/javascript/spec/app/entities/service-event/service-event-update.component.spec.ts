/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEventUpdateComponent } from 'app/entities/service-event/service-event-update.component';
import { ServiceEventService } from 'app/entities/service-event/service-event.service';
import { ServiceEvent } from 'app/shared/model/service-event.model';

describe('Component Tests', () => {
  describe('ServiceEvent Management Update Component', () => {
    let comp: ServiceEventUpdateComponent;
    let fixture: ComponentFixture<ServiceEventUpdateComponent>;
    let service: ServiceEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEventUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceEvent(123);
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
        const entity = new ServiceEvent();
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
