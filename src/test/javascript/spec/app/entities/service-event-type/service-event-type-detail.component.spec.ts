/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEventTypeDetailComponent } from 'app/entities/service-event-type/service-event-type-detail.component';
import { ServiceEventType } from 'app/shared/model/service-event-type.model';

describe('Component Tests', () => {
  describe('ServiceEventType Management Detail Component', () => {
    let comp: ServiceEventTypeDetailComponent;
    let fixture: ComponentFixture<ServiceEventTypeDetailComponent>;
    const route = ({ data: of({ serviceEventType: new ServiceEventType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEventTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceEventTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceEventTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceEventType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
