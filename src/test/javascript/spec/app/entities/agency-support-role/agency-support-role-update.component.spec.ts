/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencySupportRoleUpdateComponent } from 'app/entities/agency-support-role/agency-support-role-update.component';
import { AgencySupportRoleService } from 'app/entities/agency-support-role/agency-support-role.service';
import { AgencySupportRole } from 'app/shared/model/agency-support-role.model';

describe('Component Tests', () => {
  describe('AgencySupportRole Management Update Component', () => {
    let comp: AgencySupportRoleUpdateComponent;
    let fixture: ComponentFixture<AgencySupportRoleUpdateComponent>;
    let service: AgencySupportRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencySupportRoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AgencySupportRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgencySupportRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgencySupportRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AgencySupportRole(123);
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
        const entity = new AgencySupportRole();
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
