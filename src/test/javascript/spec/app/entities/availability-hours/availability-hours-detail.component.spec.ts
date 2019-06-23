/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AvailabilityHoursDetailComponent } from 'app/entities/availability-hours/availability-hours-detail.component';
import { AvailabilityHours } from 'app/shared/model/availability-hours.model';

describe('Component Tests', () => {
  describe('AvailabilityHours Management Detail Component', () => {
    let comp: AvailabilityHoursDetailComponent;
    let fixture: ComponentFixture<AvailabilityHoursDetailComponent>;
    const route = ({ data: of({ availabilityHours: new AvailabilityHours(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AvailabilityHoursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AvailabilityHoursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvailabilityHoursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.availabilityHours).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
