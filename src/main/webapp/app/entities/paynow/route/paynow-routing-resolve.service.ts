import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaynow, Paynow } from '../paynow.model';
import { PaynowService } from '../service/paynow.service';

@Injectable({ providedIn: 'root' })
export class PaynowRoutingResolveService implements Resolve<IPaynow> {
  constructor(protected service: PaynowService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaynow> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paynow: HttpResponse<Paynow>) => {
          if (paynow.body) {
            return of(paynow.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Paynow());
  }
}
