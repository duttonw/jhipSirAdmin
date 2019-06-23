/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceSupportRoleUpdateComponent } from 'app/entities/service-support-role/service-support-role-update.component';
import { ServiceSupportRoleService } from 'app/entities/service-support-role/service-support-role.service';
import { ServiceSupportRole } from 'app/shared/model/service-support-role.model';

describe('Component Tests', () => {
  describe('ServiceSupportRole Management Update Component', () => {
    let comp: ServiceSupportRoleUpdateComponent;
    let fixture: ComponentFixture<ServiceSupportRoleUpdateComponent>;
    let service: ServiceSupportRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceSupportRoleUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceSupportRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceSupportRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceSupportRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceSupportRole(123);
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
        const entity = new ServiceSupportRole();
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
