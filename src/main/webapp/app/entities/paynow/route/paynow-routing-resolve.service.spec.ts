jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IPaynow, Paynow } from '../paynow.model';
import { PaynowService } from '../service/paynow.service';

import { PaynowRoutingResolveService } from './paynow-routing-resolve.service';

describe('Paynow routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PaynowRoutingResolveService;
  let service: PaynowService;
  let resultPaynow: IPaynow | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(PaynowRoutingResolveService);
    service = TestBed.inject(PaynowService);
    resultPaynow = undefined;
  });

  describe('resolve', () => {
    it('should return IPaynow returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaynow = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaynow).toEqual({ id: 123 });
    });

    it('should return new IPaynow if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaynow = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPaynow).toEqual(new Paynow());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Paynow })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPaynow = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPaynow).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
