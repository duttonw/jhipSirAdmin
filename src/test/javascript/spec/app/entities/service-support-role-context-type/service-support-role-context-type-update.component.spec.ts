/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceSupportRoleContextTypeUpdateComponent } from 'app/entities/service-support-role-context-type/service-support-role-context-type-update.component';
import { ServiceSupportRoleContextTypeService } from 'app/entities/service-support-role-context-type/service-support-role-context-type.service';
import { ServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';

describe('Component Tests', () => {
  describe('ServiceSupportRoleContextType Management Update Component', () => {
    let comp: ServiceSupportRoleContextTypeUpdateComponent;
    let fixture: ComponentFixture<ServiceSupportRoleContextTypeUpdateComponent>;
    let service: ServiceSupportRoleContextTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceSupportRoleContextTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceSupportRoleContextTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceSupportRoleContextTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceSupportRoleContextTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceSupportRoleContextType(123);
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
        const entity = new ServiceSupportRoleContextType();
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
