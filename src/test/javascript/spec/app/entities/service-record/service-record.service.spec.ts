/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ServiceRecordService } from 'app/entities/service-record/service-record.service';
import { IServiceRecord, ServiceRecord } from 'app/shared/model/service-record.model';

describe('Service Tests', () => {
  describe('ServiceRecord Service', () => {
    let injector: TestBed;
    let service: ServiceRecordService;
    let httpMock: HttpTestingController;
    let elemDefault: IServiceRecord;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ServiceRecordService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ServiceRecord(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            validatedDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ServiceRecord', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            validatedDate: currentDate.format(DATE_TIME_FORMAT),
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate,
            validatedDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new ServiceRecord(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ServiceRecord', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            version: 1,
            active: 'BBBBBB',
            eligibility: 'BBBBBB',
            fees: 'BBBBBB',
            groupHeader: 'BBBBBB',
            groupId: 'BBBBBB',
            interactionId: 'BBBBBB',
            keywords: 'BBBBBB',
            preRequisites: 'BBBBBB',
            qgsServiceId: 'BBBBBB',
            referenceUrl: 'BBBBBB',
            serviceName: 'BBBBBB',
            validatedDate: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            preRequisitesNew: 'BBBBBB',
            referenceUrlNew: 'BBBBBB',
            eligibilityNew: 'BBBBBB',
            serviceContext: 'BBBBBB',
            longDescription: 'BBBBBB',
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            roadmapLoginRequired: 'BBBBBB',
            roadmapCustomerIdRequired: 'BBBBBB',
            roadmapCustomerDetails: 'BBBBBB',
            roadmapImproveIntention: 'BBBBBB',
            roadmapImproveFuture: 'BBBBBB',
            roadmapImproveType: 'BBBBBB',
            roadmapImproveWhen: 'BBBBBB',
            roadmapImproveHow: 'BBBBBB',
            roadmapMaturityCurrent: 'BBBBBB',
            roadmapMaturityDesired: 'BBBBBB',
            roadmapComments: 'BBBBBB',
            howTo: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate,
            validatedDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ServiceRecord', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            version: 1,
            active: 'BBBBBB',
            eligibility: 'BBBBBB',
            fees: 'BBBBBB',
            groupHeader: 'BBBBBB',
            groupId: 'BBBBBB',
            interactionId: 'BBBBBB',
            keywords: 'BBBBBB',
            preRequisites: 'BBBBBB',
            qgsServiceId: 'BBBBBB',
            referenceUrl: 'BBBBBB',
            serviceName: 'BBBBBB',
            validatedDate: currentDate.format(DATE_TIME_FORMAT),
            description: 'BBBBBB',
            preRequisitesNew: 'BBBBBB',
            referenceUrlNew: 'BBBBBB',
            eligibilityNew: 'BBBBBB',
            serviceContext: 'BBBBBB',
            longDescription: 'BBBBBB',
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            roadmapLoginRequired: 'BBBBBB',
            roadmapCustomerIdRequired: 'BBBBBB',
            roadmapCustomerDetails: 'BBBBBB',
            roadmapImproveIntention: 'BBBBBB',
            roadmapImproveFuture: 'BBBBBB',
            roadmapImproveType: 'BBBBBB',
            roadmapImproveWhen: 'BBBBBB',
            roadmapImproveHow: 'BBBBBB',
            roadmapMaturityCurrent: 'BBBBBB',
            roadmapMaturityDesired: 'BBBBBB',
            roadmapComments: 'BBBBBB',
            howTo: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate,
            validatedDate: currentDate,
            startDate: currentDate,
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ServiceRecord', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
