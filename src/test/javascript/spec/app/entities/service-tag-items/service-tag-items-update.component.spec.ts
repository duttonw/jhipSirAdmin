/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceTagItemsUpdateComponent } from 'app/entities/service-tag-items/service-tag-items-update.component';
import { ServiceTagItemsService } from 'app/entities/service-tag-items/service-tag-items.service';
import { ServiceTagItems } from 'app/shared/model/service-tag-items.model';

describe('Component Tests', () => {
  describe('ServiceTagItems Management Update Component', () => {
    let comp: ServiceTagItemsUpdateComponent;
    let fixture: ComponentFixture<ServiceTagItemsUpdateComponent>;
    let service: ServiceTagItemsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceTagItemsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceTagItemsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceTagItemsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceTagItemsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceTagItems(123);
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
        const entity = new ServiceTagItems();
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
