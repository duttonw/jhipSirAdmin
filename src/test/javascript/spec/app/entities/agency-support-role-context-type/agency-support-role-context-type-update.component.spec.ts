/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencySupportRoleContextTypeUpdateComponent } from 'app/entities/agency-support-role-context-type/agency-support-role-context-type-update.component';
import { AgencySupportRoleContextTypeService } from 'app/entities/agency-support-role-context-type/agency-support-role-context-type.service';
import { AgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';

describe('Component Tests', () => {
  describe('AgencySupportRoleContextType Management Update Component', () => {
    let comp: AgencySupportRoleContextTypeUpdateComponent;
    let fixture: ComponentFixture<AgencySupportRoleContextTypeUpdateComponent>;
    let service: AgencySupportRoleContextTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencySupportRoleContextTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AgencySupportRoleContextTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgencySupportRoleContextTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgencySupportRoleContextTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AgencySupportRoleContextType(123);
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
        const entity = new AgencySupportRoleContextType();
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
