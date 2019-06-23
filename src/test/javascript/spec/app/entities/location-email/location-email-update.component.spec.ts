/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationEmailUpdateComponent } from 'app/entities/location-email/location-email-update.component';
import { LocationEmailService } from 'app/entities/location-email/location-email.service';
import { LocationEmail } from 'app/shared/model/location-email.model';

describe('Component Tests', () => {
  describe('LocationEmail Management Update Component', () => {
    let comp: LocationEmailUpdateComponent;
    let fixture: ComponentFixture<LocationEmailUpdateComponent>;
    let service: LocationEmailService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationEmailUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocationEmailUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationEmailUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationEmailService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LocationEmail(123);
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
        const entity = new LocationEmail();
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
