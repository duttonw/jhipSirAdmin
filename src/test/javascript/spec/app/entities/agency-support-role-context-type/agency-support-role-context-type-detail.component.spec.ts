/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { AgencySupportRoleContextTypeDetailComponent } from 'app/entities/agency-support-role-context-type/agency-support-role-context-type-detail.component';
import { AgencySupportRoleContextType } from 'app/shared/model/agency-support-role-context-type.model';

describe('Component Tests', () => {
  describe('AgencySupportRoleContextType Management Detail Component', () => {
    let comp: AgencySupportRoleContextTypeDetailComponent;
    let fixture: ComponentFixture<AgencySupportRoleContextTypeDetailComponent>;
    const route = ({ data: of({ agencySupportRoleContextType: new AgencySupportRoleContextType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [AgencySupportRoleContextTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AgencySupportRoleContextTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgencySupportRoleContextTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agencySupportRoleContextType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
