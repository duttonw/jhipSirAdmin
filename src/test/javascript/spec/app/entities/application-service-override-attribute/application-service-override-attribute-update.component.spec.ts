/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideAttributeUpdateComponent } from 'app/entities/application-service-override-attribute/application-service-override-attribute-update.component';
import { ApplicationServiceOverrideAttributeService } from 'app/entities/application-service-override-attribute/application-service-override-attribute.service';
import { ApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideAttribute Management Update Component', () => {
    let comp: ApplicationServiceOverrideAttributeUpdateComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideAttributeUpdateComponent>;
    let service: ApplicationServiceOverrideAttributeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideAttributeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationServiceOverrideAttributeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationServiceOverrideAttributeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideAttributeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationServiceOverrideAttribute(123);
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
        const entity = new ApplicationServiceOverrideAttribute();
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
