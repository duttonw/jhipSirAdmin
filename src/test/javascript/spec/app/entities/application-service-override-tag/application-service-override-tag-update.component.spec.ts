/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideTagUpdateComponent } from 'app/entities/application-service-override-tag/application-service-override-tag-update.component';
import { ApplicationServiceOverrideTagService } from 'app/entities/application-service-override-tag/application-service-override-tag.service';
import { ApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideTag Management Update Component', () => {
    let comp: ApplicationServiceOverrideTagUpdateComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideTagUpdateComponent>;
    let service: ApplicationServiceOverrideTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideTagUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationServiceOverrideTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationServiceOverrideTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationServiceOverrideTag(123);
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
        const entity = new ApplicationServiceOverrideTag();
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
