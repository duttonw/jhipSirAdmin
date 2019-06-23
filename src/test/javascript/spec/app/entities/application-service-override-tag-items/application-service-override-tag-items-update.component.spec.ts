/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideTagItemsUpdateComponent } from 'app/entities/application-service-override-tag-items/application-service-override-tag-items-update.component';
import { ApplicationServiceOverrideTagItemsService } from 'app/entities/application-service-override-tag-items/application-service-override-tag-items.service';
import { ApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideTagItems Management Update Component', () => {
    let comp: ApplicationServiceOverrideTagItemsUpdateComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideTagItemsUpdateComponent>;
    let service: ApplicationServiceOverrideTagItemsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideTagItemsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ApplicationServiceOverrideTagItemsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ApplicationServiceOverrideTagItemsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ApplicationServiceOverrideTagItemsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ApplicationServiceOverrideTagItems(123);
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
        const entity = new ApplicationServiceOverrideTagItems();
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
