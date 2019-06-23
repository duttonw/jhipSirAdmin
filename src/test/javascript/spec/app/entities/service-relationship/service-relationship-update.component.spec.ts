/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRelationshipUpdateComponent } from 'app/entities/service-relationship/service-relationship-update.component';
import { ServiceRelationshipService } from 'app/entities/service-relationship/service-relationship.service';
import { ServiceRelationship } from 'app/shared/model/service-relationship.model';

describe('Component Tests', () => {
  describe('ServiceRelationship Management Update Component', () => {
    let comp: ServiceRelationshipUpdateComponent;
    let fixture: ComponentFixture<ServiceRelationshipUpdateComponent>;
    let service: ServiceRelationshipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRelationshipUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceRelationshipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceRelationshipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRelationshipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceRelationship(123);
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
        const entity = new ServiceRelationship();
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
