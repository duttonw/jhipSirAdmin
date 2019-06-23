/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceDescriptionDetailComponent } from 'app/entities/service-description/service-description-detail.component';
import { ServiceDescription } from 'app/shared/model/service-description.model';

describe('Component Tests', () => {
  describe('ServiceDescription Management Detail Component', () => {
    let comp: ServiceDescriptionDetailComponent;
    let fixture: ComponentFixture<ServiceDescriptionDetailComponent>;
    const route = ({ data: of({ serviceDescription: new ServiceDescription(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceDescriptionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceDescriptionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceDescriptionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceDescription).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
