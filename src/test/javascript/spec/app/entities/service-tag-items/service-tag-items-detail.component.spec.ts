/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceTagItemsDetailComponent } from 'app/entities/service-tag-items/service-tag-items-detail.component';
import { ServiceTagItems } from 'app/shared/model/service-tag-items.model';

describe('Component Tests', () => {
  describe('ServiceTagItems Management Detail Component', () => {
    let comp: ServiceTagItemsDetailComponent;
    let fixture: ComponentFixture<ServiceTagItemsDetailComponent>;
    const route = ({ data: of({ serviceTagItems: new ServiceTagItems(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceTagItemsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceTagItemsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceTagItemsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceTagItems).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
