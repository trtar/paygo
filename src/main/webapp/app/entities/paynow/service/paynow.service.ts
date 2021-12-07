import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaynow, getPaynowIdentifier } from '../paynow.model';

export type EntityResponseType = HttpResponse<IPaynow>;
export type EntityArrayResponseType = HttpResponse<IPaynow[]>;

@Injectable({ providedIn: 'root' })
export class PaynowService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/paynows');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paynow: IPaynow): Observable<EntityResponseType> {
    return this.http.post<IPaynow>(this.resourceUrl, paynow, { observe: 'response' });
  }

  update(paynow: IPaynow): Observable<EntityResponseType> {
    return this.http.put<IPaynow>(`${this.resourceUrl}/${getPaynowIdentifier(paynow) as number}`, paynow, { observe: 'response' });
  }

  partialUpdate(paynow: IPaynow): Observable<EntityResponseType> {
    return this.http.patch<IPaynow>(`${this.resourceUrl}/${getPaynowIdentifier(paynow) as number}`, paynow, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPaynow>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaynow[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaynowToCollectionIfMissing(paynowCollection: IPaynow[], ...paynowsToCheck: (IPaynow | null | undefined)[]): IPaynow[] {
    const paynows: IPaynow[] = paynowsToCheck.filter(isPresent);
    if (paynows.length > 0) {
      const paynowCollectionIdentifiers = paynowCollection.map(paynowItem => getPaynowIdentifier(paynowItem)!);
      const paynowsToAdd = paynows.filter(paynowItem => {
        const paynowIdentifier = getPaynowIdentifier(paynowItem);
        if (paynowIdentifier == null || paynowCollectionIdentifiers.includes(paynowIdentifier)) {
          return false;
        }
        paynowCollectionIdentifiers.push(paynowIdentifier);
        return true;
      });
      return [...paynowsToAdd, ...paynowCollection];
    }
    return paynowCollection;
  }
}
