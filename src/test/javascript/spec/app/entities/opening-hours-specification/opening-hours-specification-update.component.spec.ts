/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { OpeningHoursSpecificationUpdateComponent } from 'app/entities/opening-hours-specification/opening-hours-specification-update.component';
import { OpeningHoursSpecificationService } from 'app/entities/opening-hours-specification/opening-hours-specification.service';
import { OpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';

describe('Component Tests', () => {
  describe('OpeningHoursSpecification Management Update Component', () => {
    let comp: OpeningHoursSpecificationUpdateComponent;
    let fixture: ComponentFixture<OpeningHoursSpecificationUpdateComponent>;
    let service: OpeningHoursSpecificationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [OpeningHoursSpecificationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OpeningHoursSpecificationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpeningHoursSpecificationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpeningHoursSpecificationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OpeningHoursSpecification(123);
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
        const entity = new OpeningHoursSpecification();
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
