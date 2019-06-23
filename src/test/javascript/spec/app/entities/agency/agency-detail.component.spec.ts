/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencyDetailComponent } from 'app/entities/agency/agency-detail.component';
import { Agency } from 'app/shared/model/agency.model';

describe('Component Tests', () => {
  describe('Agency Management Detail Component', () => {
    let comp: AgencyDetailComponent;
    let fixture: ComponentFixture<AgencyDetailComponent>;
    const route = ({ data: of({ agency: new Agency(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgencyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgencyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agency).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
