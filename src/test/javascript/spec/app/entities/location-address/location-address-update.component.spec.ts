/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationAddressUpdateComponent } from 'app/entities/location-address/location-address-update.component';
import { LocationAddressService } from 'app/entities/location-address/location-address.service';
import { LocationAddress } from 'app/shared/model/location-address.model';

describe('Component Tests', () => {
  describe('LocationAddress Management Update Component', () => {
    let comp: LocationAddressUpdateComponent;
    let fixture: ComponentFixture<LocationAddressUpdateComponent>;
    let service: LocationAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationAddressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LocationAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocationAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LocationAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LocationAddress(123);
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
        const entity = new LocationAddress();
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
