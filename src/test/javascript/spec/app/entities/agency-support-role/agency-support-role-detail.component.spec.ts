/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencySupportRoleDetailComponent } from 'app/entities/agency-support-role/agency-support-role-detail.component';
import { AgencySupportRole } from 'app/shared/model/agency-support-role.model';

describe('Component Tests', () => {
  describe('AgencySupportRole Management Detail Component', () => {
    let comp: AgencySupportRoleDetailComponent;
    let fixture: ComponentFixture<AgencySupportRoleDetailComponent>;
    const route = ({ data: of({ agencySupportRole: new AgencySupportRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencySupportRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgencySupportRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgencySupportRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agencySupportRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
