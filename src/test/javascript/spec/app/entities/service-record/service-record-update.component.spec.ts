/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRecordUpdateComponent } from 'app/entities/service-record/service-record-update.component';
import { ServiceRecordService } from 'app/entities/service-record/service-record.service';
import { ServiceRecord } from 'app/shared/model/service-record.model';

describe('Component Tests', () => {
  describe('ServiceRecord Management Update Component', () => {
    let comp: ServiceRecordUpdateComponent;
    let fixture: ComponentFixture<ServiceRecordUpdateComponent>;
    let service: ServiceRecordService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRecordUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ServiceRecordUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceRecordUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceRecordService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceRecord(123);
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
        const entity = new ServiceRecord();
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
