import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Confirm } from './confirm';

@Injectable({
  providedIn: 'root',
})
export class ConfirmService {
  /* eslint-disable */
  private baseURL = 'api/paynows';

  constructor(private http: HttpClient) {}

  createAppointment(confirm: Confirm): Observable<any> {
    return this.http.post(`${this.baseURL}`, confirm);
  }
}
