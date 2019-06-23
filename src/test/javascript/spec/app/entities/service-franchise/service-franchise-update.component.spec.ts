/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceFranchiseUpdateComponent } from 'app/entities/service-franchise/service-franchise-update.component';
import { ServiceFranchiseService } from 'app/entities/service-franchise/service-franchise.service';
import { ServiceFranchise } from 'app/shared/model/service-franchise.model';

describe('Component Tests', () => {
  describe('ServiceFranchise Management Update Component', () => {
    let comp: ServiceFranchiseUpdateComponent;
    let fixture: ComponentFixture<ServiceFranchiseUpdateComponent>;
    let service: ServiceFranchiseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceFranchiseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceFranchiseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceFranchiseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceFranchiseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceFranchise(123);
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
        const entity = new ServiceFranchise();
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
