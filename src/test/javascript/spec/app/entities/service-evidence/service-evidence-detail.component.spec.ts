/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceEvidenceDetailComponent } from 'app/entities/service-evidence/service-evidence-detail.component';
import { ServiceEvidence } from 'app/shared/model/service-evidence.model';

describe('Component Tests', () => {
  describe('ServiceEvidence Management Detail Component', () => {
    let comp: ServiceEvidenceDetailComponent;
    let fixture: ComponentFixture<ServiceEvidenceDetailComponent>;
    const route = ({ data: of({ serviceEvidence: new ServiceEvidence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceEvidenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceEvidenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceEvidenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceEvidence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
