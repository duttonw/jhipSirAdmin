/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { LocationEmailDetailComponent } from 'app/entities/location-email/location-email-detail.component';
import { LocationEmail } from 'app/shared/model/location-email.model';

describe('Component Tests', () => {
  describe('LocationEmail Management Detail Component', () => {
    let comp: LocationEmailDetailComponent;
    let fixture: ComponentFixture<LocationEmailDetailComponent>;
    const route = ({ data: of({ locationEmail: new LocationEmail(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [LocationEmailDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LocationEmailDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocationEmailDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locationEmail).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
