import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
/* eslint-disable */

@Injectable({
  providedIn: 'root',
})
export class SharedConfirmService {
  cik: any;
  ccc: any;
  paymount: any;
  name: any;
  email: any;
  phone: any;

  constructor(private httpClient: HttpClient) {}

  setMessage(cik: any, ccc: any, paymount: any, name: any, email: any, phone: any) {
    this.cik = cik;
    this.ccc = ccc;
    this.paymount = paymount;
    this.name = name;
    this.email = email;
    this.phone = phone;
    console.warn(email + ' Email');
    console.warn(this.paymount + ' amount');
  }
  getMessage() {
    return new Array(this.cik, this.ccc, this.paymount, this.name, this.email, this.phone);
  }
  getRedirection() {
    this.httpClient.get('http://localhost:8080/to-be-redirected');
  }
}
