import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPaynow, Paynow } from '../paynow.model';

import { PaynowService } from './paynow.service';

describe('Paynow Service', () => {
  let service: PaynowService;
  let httpMock: HttpTestingController;
  let elemDefault: IPaynow;
  let expectedResult: IPaynow | IPaynow[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PaynowService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      cik: 'AAAAAAA',
      ccc: 'AAAAAAA',
      name: 'AAAAAAA',
      email: 'AAAAAAA',
      phone: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Paynow', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Paynow()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Paynow', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cik: 'BBBBBB',
          ccc: 'BBBBBB',
          name: 'BBBBBB',
          email: 'BBBBBB',
          phone: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Paynow', () => {
      const patchObject = Object.assign(
        {
          cik: 'BBBBBB',
          email: 'BBBBBB',
        },
        new Paynow()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Paynow', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          cik: 'BBBBBB',
          ccc: 'BBBBBB',
          name: 'BBBBBB',
          email: 'BBBBBB',
          phone: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Paynow', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPaynowToCollectionIfMissing', () => {
      it('should add a Paynow to an empty array', () => {
        const paynow: IPaynow = { id: 123 };
        expectedResult = service.addPaynowToCollectionIfMissing([], paynow);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paynow);
      });

      it('should not add a Paynow to an array that contains it', () => {
        const paynow: IPaynow = { id: 123 };
        const paynowCollection: IPaynow[] = [
          {
            ...paynow,
          },
          { id: 456 },
        ];
        expectedResult = service.addPaynowToCollectionIfMissing(paynowCollection, paynow);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Paynow to an array that doesn't contain it", () => {
        const paynow: IPaynow = { id: 123 };
        const paynowCollection: IPaynow[] = [{ id: 456 }];
        expectedResult = service.addPaynowToCollectionIfMissing(paynowCollection, paynow);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paynow);
      });

      it('should add only unique Paynow to an array', () => {
        const paynowArray: IPaynow[] = [{ id: 123 }, { id: 456 }, { id: 15919 }];
        const paynowCollection: IPaynow[] = [{ id: 123 }];
        expectedResult = service.addPaynowToCollectionIfMissing(paynowCollection, ...paynowArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const paynow: IPaynow = { id: 123 };
        const paynow2: IPaynow = { id: 456 };
        expectedResult = service.addPaynowToCollectionIfMissing([], paynow, paynow2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(paynow);
        expect(expectedResult).toContain(paynow2);
      });

      it('should accept null and undefined values', () => {
        const paynow: IPaynow = { id: 123 };
        expectedResult = service.addPaynowToCollectionIfMissing([], null, paynow, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(paynow);
      });

      it('should return initial array if no Paynow is added', () => {
        const paynowCollection: IPaynow[] = [{ id: 123 }];
        expectedResult = service.addPaynowToCollectionIfMissing(paynowCollection, undefined, null);
        expect(expectedResult).toEqual(paynowCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
