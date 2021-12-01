import { Injectable } from '@angular/core';
/* eslint-disable */

@Injectable({
  providedIn: 'root',
})
export class SharedConfirmService {
  cik: any;
  ccc: any;
  payAmount: any;
  name: any;
  email: any;
  phone: any;

  constructor() {}

  setMessage(cik: any, ccc: any, amount: any, name: any, email: any, phone: any) {
    this.cik = cik;
    this.ccc = ccc;
    this.payAmount = amount;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }
  getMessage() {
    return new Array(this.cik, this.ccc, this.payAmount, this.name, this.email, this.phone);
  }
}
