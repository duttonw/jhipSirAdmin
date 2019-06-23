/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRelationshipDetailComponent } from 'app/entities/service-relationship/service-relationship-detail.component';
import { ServiceRelationship } from 'app/shared/model/service-relationship.model';

describe('Component Tests', () => {
  describe('ServiceRelationship Management Detail Component', () => {
    let comp: ServiceRelationshipDetailComponent;
    let fixture: ComponentFixture<ServiceRelationshipDetailComponent>;
    const route = ({ data: of({ serviceRelationship: new ServiceRelationship(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRelationshipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceRelationshipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRelationshipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceRelationship).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
