/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDeliveryOrganisationDetailComponent } from 'app/entities/service-delivery-organisation/service-delivery-organisation-detail.component';
import { ServiceDeliveryOrganisation } from 'app/shared/model/service-delivery-organisation.model';

describe('Component Tests', () => {
  describe('ServiceDeliveryOrganisation Management Detail Component', () => {
    let comp: ServiceDeliveryOrganisationDetailComponent;
    let fixture: ComponentFixture<ServiceDeliveryOrganisationDetailComponent>;
    const route = ({ data: of({ serviceDeliveryOrganisation: new ServiceDeliveryOrganisation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDeliveryOrganisationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceDeliveryOrganisationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDeliveryOrganisationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceDeliveryOrganisation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
