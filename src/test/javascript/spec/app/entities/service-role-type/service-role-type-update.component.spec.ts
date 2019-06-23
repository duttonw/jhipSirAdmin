/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRoleTypeUpdateComponent } from 'app/entities/service-role-type/service-role-type-update.component';
import { ServiceRoleTypeService } from 'app/entities/service-role-type/service-role-type.service';
import { ServiceRoleType } from 'app/shared/model/service-role-type.model';

describe('Component Tests', () => {
  describe('ServiceRoleType Management Update Component', () => {
    let comp: ServiceRoleTypeUpdateComponent;
    let fixture: ComponentFixture<ServiceRoleTypeUpdateComponent>;
    let service: ServiceRoleTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRoleTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceRoleTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceRoleTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRoleTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceRoleType(123);
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
        const entity = new ServiceRoleType();
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
