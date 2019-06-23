/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceFranchiseDetailComponent } from 'app/entities/service-franchise/service-franchise-detail.component';
import { ServiceFranchise } from 'app/shared/model/service-franchise.model';

describe('Component Tests', () => {
  describe('ServiceFranchise Management Detail Component', () => {
    let comp: ServiceFranchiseDetailComponent;
    let fixture: ComponentFixture<ServiceFranchiseDetailComponent>;
    const route = ({ data: of({ serviceFranchise: new ServiceFranchise(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceFranchiseDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceFranchiseDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceFranchiseDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceFranchise).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
