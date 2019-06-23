/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEvidenceUpdateComponent } from 'app/entities/service-evidence/service-evidence-update.component';
import { ServiceEvidenceService } from 'app/entities/service-evidence/service-evidence.service';
import { ServiceEvidence } from 'app/shared/model/service-evidence.model';

describe('Component Tests', () => {
  describe('ServiceEvidence Management Update Component', () => {
    let comp: ServiceEvidenceUpdateComponent;
    let fixture: ComponentFixture<ServiceEvidenceUpdateComponent>;
    let service: ServiceEvidenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEvidenceUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceEvidenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceEvidenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceEvidenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceEvidence(123);
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
        const entity = new ServiceEvidence();
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
