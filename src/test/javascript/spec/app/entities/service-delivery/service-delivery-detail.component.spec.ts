/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryDetailComponent } from 'app/entities/service-delivery/service-delivery-detail.component';
import { ServiceDelivery } from 'app/shared/model/service-delivery.model';

describe('Component Tests', () => {
  describe('ServiceDelivery Management Detail Component', () => {
    let comp: ServiceDeliveryDetailComponent;
    let fixture: ComponentFixture<ServiceDeliveryDetailComponent>;
    const route = ({ data: of({ serviceDelivery: new ServiceDelivery(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceDeliveryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDeliveryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceDelivery).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
