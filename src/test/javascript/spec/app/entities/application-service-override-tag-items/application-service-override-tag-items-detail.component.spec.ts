/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideTagItemsDetailComponent } from 'app/entities/application-service-override-tag-items/application-service-override-tag-items-detail.component';
import { ApplicationServiceOverrideTagItems } from 'app/shared/model/application-service-override-tag-items.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideTagItems Management Detail Component', () => {
    let comp: ApplicationServiceOverrideTagItemsDetailComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideTagItemsDetailComponent>;
    const route = ({
      data: of({ applicationServiceOverrideTagItems: new ApplicationServiceOverrideTagItems(123) })
    } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideTagItemsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationServiceOverrideTagItemsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideTagItemsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationServiceOverrideTagItems).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
