/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceNameDetailComponent } from 'app/entities/service-name/service-name-detail.component';
import { ServiceName } from 'app/shared/model/service-name.model';

describe('Component Tests', () => {
  describe('ServiceName Management Detail Component', () => {
    let comp: ServiceNameDetailComponent;
    let fixture: ComponentFixture<ServiceNameDetailComponent>;
    const route = ({ data: of({ serviceName: new ServiceName(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceNameDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceNameDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceNameDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceName).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
