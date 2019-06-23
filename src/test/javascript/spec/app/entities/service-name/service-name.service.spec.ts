/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ServiceNameService } from 'app/entities/service-name/service-name.service';
import { IServiceName, ServiceName } from 'app/shared/model/service-name.model';

describe('Service Tests', () => {
  describe('ServiceName Service', () => {
    let injector: TestBed;
    let service: ServiceNameService;
    let httpMock: HttpTestingController;
    let elemDefault: IServiceName;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ServiceNameService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ServiceName(0, 'AAAAAAA', currentDate, 'AAAAAAA', currentDate, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a ServiceName', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate
          },
          returnedFromService
        );
        service
          .create(new ServiceName(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ServiceName', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            version: 1,
            context: 'BBBBBB',
            serviceName: 'BBBBBB',
            migrated: 'BBBBBB',
            migratedBy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate
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

      it('should return a list of ServiceName', async () => {
        const returnedFromService = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            version: 1,
            context: 'BBBBBB',
            serviceName: 'BBBBBB',
            migrated: 'BBBBBB',
            migratedBy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            createdDateTime: currentDate,
            modifiedDateTime: currentDate
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

      it('should delete a ServiceName', async () => {
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
