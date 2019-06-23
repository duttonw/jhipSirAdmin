/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { OpeningHoursSpecificationDetailComponent } from 'app/entities/opening-hours-specification/opening-hours-specification-detail.component';
import { OpeningHoursSpecification } from 'app/shared/model/opening-hours-specification.model';

describe('Component Tests', () => {
  describe('OpeningHoursSpecification Management Detail Component', () => {
    let comp: OpeningHoursSpecificationDetailComponent;
    let fixture: ComponentFixture<OpeningHoursSpecificationDetailComponent>;
    const route = ({ data: of({ openingHoursSpecification: new OpeningHoursSpecification(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [OpeningHoursSpecificationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OpeningHoursSpecificationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpeningHoursSpecificationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.openingHoursSpecification).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
