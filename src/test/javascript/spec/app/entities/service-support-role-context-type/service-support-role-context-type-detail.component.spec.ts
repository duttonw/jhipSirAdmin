/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceSupportRoleContextTypeDetailComponent } from 'app/entities/service-support-role-context-type/service-support-role-context-type-detail.component';
import { ServiceSupportRoleContextType } from 'app/shared/model/service-support-role-context-type.model';

describe('Component Tests', () => {
  describe('ServiceSupportRoleContextType Management Detail Component', () => {
    let comp: ServiceSupportRoleContextTypeDetailComponent;
    let fixture: ComponentFixture<ServiceSupportRoleContextTypeDetailComponent>;
    const route = ({ data: of({ serviceSupportRoleContextType: new ServiceSupportRoleContextType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceSupportRoleContextTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceSupportRoleContextTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceSupportRoleContextTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceSupportRoleContextType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
