/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipSirAdminTestModule } from '../../../test.module';
import { ServiceRecordDetailComponent } from 'app/entities/service-record/service-record-detail.component';
import { ServiceRecord } from 'app/shared/model/service-record.model';

describe('Component Tests', () => {
  describe('ServiceRecord Management Detail Component', () => {
    let comp: ServiceRecordDetailComponent;
    let fixture: ComponentFixture<ServiceRecordDetailComponent>;
    const route = ({ data: of({ serviceRecord: new ServiceRecord(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JhipSirAdminTestModule],
        declarations: [ServiceRecordDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceRecordDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceRecordDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceRecord).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
