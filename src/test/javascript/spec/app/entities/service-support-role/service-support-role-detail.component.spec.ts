/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceSupportRoleDetailComponent } from 'app/entities/service-support-role/service-support-role-detail.component';
import { ServiceSupportRole } from 'app/shared/model/service-support-role.model';

describe('Component Tests', () => {
  describe('ServiceSupportRole Management Detail Component', () => {
    let comp: ServiceSupportRoleDetailComponent;
    let fixture: ComponentFixture<ServiceSupportRoleDetailComponent>;
    const route = ({ data: of({ serviceSupportRole: new ServiceSupportRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceSupportRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceSupportRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceSupportRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceSupportRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
