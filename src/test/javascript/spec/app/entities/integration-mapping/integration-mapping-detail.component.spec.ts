/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { IntegrationMappingDetailComponent } from 'app/entities/integration-mapping/integration-mapping-detail.component';
import { IntegrationMapping } from 'app/shared/model/integration-mapping.model';

describe('Component Tests', () => {
  describe('IntegrationMapping Management Detail Component', () => {
    let comp: IntegrationMappingDetailComponent;
    let fixture: ComponentFixture<IntegrationMappingDetailComponent>;
    const route = ({ data: of({ integrationMapping: new IntegrationMapping(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [IntegrationMappingDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IntegrationMappingDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntegrationMappingDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.integrationMapping).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
