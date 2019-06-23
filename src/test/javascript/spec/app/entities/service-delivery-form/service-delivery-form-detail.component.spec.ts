/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryFormDetailComponent } from 'app/entities/service-delivery-form/service-delivery-form-detail.component';
import { ServiceDeliveryForm } from 'app/shared/model/service-delivery-form.model';

describe('Component Tests', () => {
  describe('ServiceDeliveryForm Management Detail Component', () => {
    let comp: ServiceDeliveryFormDetailComponent;
    let fixture: ComponentFixture<ServiceDeliveryFormDetailComponent>;
    const route = ({ data: of({ serviceDeliveryForm: new ServiceDeliveryForm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryFormDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceDeliveryFormDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDeliveryFormDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceDeliveryForm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
