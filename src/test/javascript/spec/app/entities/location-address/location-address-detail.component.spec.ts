/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationAddressDetailComponent } from 'app/entities/location-address/location-address-detail.component';
import { LocationAddress } from 'app/shared/model/location-address.model';

describe('Component Tests', () => {
  describe('LocationAddress Management Detail Component', () => {
    let comp: LocationAddressDetailComponent;
    let fixture: ComponentFixture<LocationAddressDetailComponent>;
    const route = ({ data: of({ locationAddress: new LocationAddress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationAddressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocationAddressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationAddressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locationAddress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
