/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ApplicationServiceOverrideAttributeService } from 'app/entities/application-service-override-attribute/application-service-override-attribute.service';
import {
  IApplicationServiceOverrideAttribute,
  ApplicationServiceOverrideAttribute
} from 'app/shared/model/application-service-override-attribute.model';

describe('Service Tests', () => {
  describe('ApplicationServiceOverrideAttribute Service', () => {
    let injector: TestBed;
    let service: ApplicationServiceOverrideAttributeService;
    let httpMock: HttpTestingController;
    let elemDefault: IApplicationServiceOverrideAttribute;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ApplicationServiceOverrideAttributeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ApplicationServiceOverrideAttribute(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        0
      );
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

      it('should create a ApplicationServiceOverrideAttribute', async () => {
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
          .create(new ApplicationServiceOverrideAttribute(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ApplicationServiceOverrideAttribute', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            value: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            migratedBy: 'BBBBBB',
            version: 1
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

      it('should return a list of ApplicationServiceOverrideAttribute', async () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            value: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDateTime: currentDate.format(DATE_TIME_FORMAT),
            modifiedBy: 'BBBBBB',
            modifiedDateTime: currentDate.format(DATE_TIME_FORMAT),
            migratedBy: 'BBBBBB',
            version: 1
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

      it('should delete a ApplicationServiceOverrideAttribute', async () => {
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
