/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceTagUpdateComponent } from 'app/entities/service-tag/service-tag-update.component';
import { ServiceTagService } from 'app/entities/service-tag/service-tag.service';
import { ServiceTag } from 'app/shared/model/service-tag.model';

describe('Component Tests', () => {
  describe('ServiceTag Management Update Component', () => {
    let comp: ServiceTagUpdateComponent;
    let fixture: ComponentFixture<ServiceTagUpdateComponent>;
    let service: ServiceTagService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceTagUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceTagUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceTagUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceTagService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceTag(123);
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
        const entity = new ServiceTag();
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
