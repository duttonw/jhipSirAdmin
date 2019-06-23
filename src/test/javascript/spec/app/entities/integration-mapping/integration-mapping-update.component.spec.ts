/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { IntegrationMappingUpdateComponent } from 'app/entities/integration-mapping/integration-mapping-update.component';
import { IntegrationMappingService } from 'app/entities/integration-mapping/integration-mapping.service';
import { IntegrationMapping } from 'app/shared/model/integration-mapping.model';

describe('Component Tests', () => {
  describe('IntegrationMapping Management Update Component', () => {
    let comp: IntegrationMappingUpdateComponent;
    let fixture: ComponentFixture<IntegrationMappingUpdateComponent>;
    let service: IntegrationMappingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [IntegrationMappingUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IntegrationMappingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntegrationMappingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntegrationMappingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IntegrationMapping(123);
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
        const entity = new IntegrationMapping();
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
