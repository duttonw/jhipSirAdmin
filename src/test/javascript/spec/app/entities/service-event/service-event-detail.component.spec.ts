/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEventDetailComponent } from 'app/entities/service-event/service-event-detail.component';
import { ServiceEvent } from 'app/shared/model/service-event.model';

describe('Component Tests', () => {
  describe('ServiceEvent Management Detail Component', () => {
    let comp: ServiceEventDetailComponent;
    let fixture: ComponentFixture<ServiceEventDetailComponent>;
    const route = ({ data: of({ serviceEvent: new ServiceEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
