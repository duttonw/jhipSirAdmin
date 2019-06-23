/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AvailabilityHoursUpdateComponent } from 'app/entities/availability-hours/availability-hours-update.component';
import { AvailabilityHoursService } from 'app/entities/availability-hours/availability-hours.service';
import { AvailabilityHours } from 'app/shared/model/availability-hours.model';

describe('Component Tests', () => {
  describe('AvailabilityHours Management Update Component', () => {
    let comp: AvailabilityHoursUpdateComponent;
    let fixture: ComponentFixture<AvailabilityHoursUpdateComponent>;
    let service: AvailabilityHoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AvailabilityHoursUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AvailabilityHoursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvailabilityHoursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvailabilityHoursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvailabilityHours(123);
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
        const entity = new AvailabilityHours();
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
