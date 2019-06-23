/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencyTypeUpdateComponent } from 'app/entities/agency-type/agency-type-update.component';
import { AgencyTypeService } from 'app/entities/agency-type/agency-type.service';
import { AgencyType } from 'app/shared/model/agency-type.model';

describe('Component Tests', () => {
  describe('AgencyType Management Update Component', () => {
    let comp: AgencyTypeUpdateComponent;
    let fixture: ComponentFixture<AgencyTypeUpdateComponent>;
    let service: AgencyTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencyTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AgencyTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgencyTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgencyTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AgencyType(123);
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
        const entity = new AgencyType();
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
