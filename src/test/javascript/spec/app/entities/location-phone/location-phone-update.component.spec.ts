/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationPhoneUpdateComponent } from 'app/entities/location-phone/location-phone-update.component';
import { LocationPhoneService } from 'app/entities/location-phone/location-phone.service';
import { LocationPhone } from 'app/shared/model/location-phone.model';

describe('Component Tests', () => {
  describe('LocationPhone Management Update Component', () => {
    let comp: LocationPhoneUpdateComponent;
    let fixture: ComponentFixture<LocationPhoneUpdateComponent>;
    let service: LocationPhoneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationPhoneUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocationPhoneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationPhoneUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationPhoneService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LocationPhone(123);
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
        const entity = new LocationPhone();
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
