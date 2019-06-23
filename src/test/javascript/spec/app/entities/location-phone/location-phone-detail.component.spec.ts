/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationPhoneDetailComponent } from 'app/entities/location-phone/location-phone-detail.component';
import { LocationPhone } from 'app/shared/model/location-phone.model';

describe('Component Tests', () => {
  describe('LocationPhone Management Detail Component', () => {
    let comp: LocationPhoneDetailComponent;
    let fixture: ComponentFixture<LocationPhoneDetailComponent>;
    const route = ({ data: of({ locationPhone: new LocationPhone(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationPhoneDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocationPhoneDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationPhoneDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locationPhone).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
