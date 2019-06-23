/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceTagDetailComponent } from 'app/entities/service-tag/service-tag-detail.component';
import { ServiceTag } from 'app/shared/model/service-tag.model';

describe('Component Tests', () => {
  describe('ServiceTag Management Detail Component', () => {
    let comp: ServiceTagDetailComponent;
    let fixture: ComponentFixture<ServiceTagDetailComponent>;
    const route = ({ data: of({ serviceTag: new ServiceTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
