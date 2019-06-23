/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceGroupUpdateComponent } from 'app/entities/service-group/service-group-update.component';
import { ServiceGroupService } from 'app/entities/service-group/service-group.service';
import { ServiceGroup } from 'app/shared/model/service-group.model';

describe('Component Tests', () => {
  describe('ServiceGroup Management Update Component', () => {
    let comp: ServiceGroupUpdateComponent;
    let fixture: ComponentFixture<ServiceGroupUpdateComponent>;
    let service: ServiceGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceGroup(123);
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
        const entity = new ServiceGroup();
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
