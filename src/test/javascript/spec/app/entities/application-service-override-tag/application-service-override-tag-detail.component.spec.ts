/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ApplicationServiceOverrideTagDetailComponent } from 'app/entities/application-service-override-tag/application-service-override-tag-detail.component';
import { ApplicationServiceOverrideTag } from 'app/shared/model/application-service-override-tag.model';

describe('Component Tests', () => {
  describe('ApplicationServiceOverrideTag Management Detail Component', () => {
    let comp: ApplicationServiceOverrideTagDetailComponent;
    let fixture: ComponentFixture<ApplicationServiceOverrideTagDetailComponent>;
    const route = ({ data: of({ applicationServiceOverrideTag: new ApplicationServiceOverrideTag(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ApplicationServiceOverrideTagDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ApplicationServiceOverrideTagDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ApplicationServiceOverrideTagDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.applicationServiceOverrideTag).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
