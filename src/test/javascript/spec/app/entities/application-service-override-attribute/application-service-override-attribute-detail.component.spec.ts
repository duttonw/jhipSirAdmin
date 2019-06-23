/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideAttributeDetailComponent } from 'app/entities/application-service-override-attribute/application-service-override-attribute-detail.component';
import { ApplicationServiceOverrideAttribute } from 'app/shared/model/application-service-override-attribute.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideAttribute Management Detail Component', () => {
    let comp: ApplicationServiceOverrideAttributeDetailComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideAttributeDetailComponent>;
    const route = ({
      data: of({ applicationServiceOverrideAttribute: new ApplicationServiceOverrideAttribute(123) })
    } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideAttributeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationServiceOverrideAttributeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideAttributeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationServiceOverrideAttribute).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
