/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideDetailComponent } from 'app/entities/application-service-override/application-service-override-detail.component';
import { ApplicationServiceOverride } from 'app/shared/model/application-service-override.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverride Management Detail Component', () => {
    let comp: ApplicationServiceOverrideDetailComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideDetailComponent>;
    const route = ({ data: of({ applicationServiceOverride: new ApplicationServiceOverride(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationServiceOverrideDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationServiceOverride).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
