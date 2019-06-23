/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRoleTypeDetailComponent } from 'app/entities/service-role-type/service-role-type-detail.component';
import { ServiceRoleType } from 'app/shared/model/service-role-type.model';

describe('Component Tests', () => {
  describe('ServiceRoleType Management Detail Component', () => {
    let comp: ServiceRoleTypeDetailComponent;
    let fixture: ComponentFixture<ServiceRoleTypeDetailComponent>;
    const route = ({ data: of({ serviceRoleType: new ServiceRoleType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRoleTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceRoleTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRoleTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceRoleType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
