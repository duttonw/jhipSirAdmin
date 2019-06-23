/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideUpdateComponent } from 'app/entities/application-service-override/application-service-override-update.component';
import { ApplicationServiceOverrideService } from 'app/entities/application-service-override/application-service-override.service';
import { ApplicationServiceOverride } from 'app/shared/model/application-service-override.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverride Management Update Component', () => {
    let comp: ApplicationServiceOverrideUpdateComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideUpdateComponent>;
    let service: ApplicationServiceOverrideService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationServiceOverrideUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationServiceOverrideUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationServiceOverride(123);
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
        const entity = new ApplicationServiceOverride();
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
