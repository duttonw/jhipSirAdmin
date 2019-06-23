/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencyTypeDetailComponent } from 'app/entities/agency-type/agency-type-detail.component';
import { AgencyType } from 'app/shared/model/agency-type.model';

describe('Component Tests', () => {
  describe('AgencyType Management Detail Component', () => {
    let comp: AgencyTypeDetailComponent;
    let fixture: ComponentFixture<AgencyTypeDetailComponent>;
    const route = ({ data: of({ agencyType: new AgencyType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencyTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgencyTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgencyTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agencyType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
