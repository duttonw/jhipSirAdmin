/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceGroupDetailComponent } from 'app/entities/service-group/service-group-detail.component';
import { ServiceGroup } from 'app/shared/model/service-group.model';

describe('Component Tests', () => {
  describe('ServiceGroup Management Detail Component', () => {
    let comp: ServiceGroupDetailComponent;
    let fixture: ComponentFixture<ServiceGroupDetailComponent>;
    const route = ({ data: of({ serviceGroup: new ServiceGroup(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceGroupDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceGroupDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceGroupDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceGroup).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
