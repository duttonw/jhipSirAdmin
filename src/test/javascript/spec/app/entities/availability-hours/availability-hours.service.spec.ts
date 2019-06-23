/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AvailabilityHoursService } from 'app/entities/availability-hours/availability-hours.service';
import { IAvailabilityHours, AvailabilityHours } from 'app/shared/model/availability-hours.model';

describe('Service Tests', () => {
  describe('AvailabilityHours Service', () => {
    let injector: TestBed;
    let service: AvailabilityHoursService;
    let httpMock: HttpTestingController;
    let elemDefault: IAvailabilityHours;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(AvailabilityHoursService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AvailabilityHours(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a AvailabilityHours', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate,
            validFrom: currentDate,
            validTo: currentDate
          },
          returnedFromService
        );
        service
          .create(new AvailabilityHours(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a AvailabilityHours', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            version: 1,
            available: 'BBBBBB',
            availabilityHoursId: 1,
            comments: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate,
            validFrom: currentDate,
            validTo: currentDate
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

      it('should return a list of AvailabilityHours', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            version: 1,
            available: 'BBBBBB',
            availabilityHoursId: 1,
            comments: 'BBBBBB',
            validFrom: currentDate.format(DATE_TIME_FORMAT),
            validTo: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate,
            validFrom: currentDate,
            validTo: currentDate
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

      it('should delete a AvailabilityHours', async () => {
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
