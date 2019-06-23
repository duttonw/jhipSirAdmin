/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceNameUpdateComponent } from 'app/entities/service-name/service-name-update.component';
import { ServiceNameService } from 'app/entities/service-name/service-name.service';
import { ServiceName } from 'app/shared/model/service-name.model';

describe('Component Tests', () => {
  describe('ServiceName Management Update Component', () => {
    let comp: ServiceNameUpdateComponent;
    let fixture: ComponentFixture<ServiceNameUpdateComponent>;
    let service: ServiceNameService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceNameUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceNameUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceNameUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceNameService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceName(123);
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
        const entity = new ServiceName();
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
