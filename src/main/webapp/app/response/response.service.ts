import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Response } from './response';

@Injectable({
  providedIn: 'root',
})
export class ResponseService {
  /* eslint-disable */
  private baseURL = 'api/paynows';

  constructor(private http: HttpClient) {}

  createAppointment(response: Response): Observable<any> {
    return this.http.post(`${this.baseURL}`, response);
  }
}
