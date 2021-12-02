import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SharedConfirmService } from 'app/shared/sharedconfirm.service';

//import { AppointmentService } from './appointment.service';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
//import { Appointment } from './appointment';

/* eslint-disable */

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit, OnDestroy {
  inputData: any;
  account: Account | null = null;
  formdata: any;
  mycik = 'dog';
  mynumber: any;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private sharedService: SharedConfirmService) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));

    this.onClickClear();
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  onClickSubmit(data: any) {
    if (this.formdata.valid) {
      console.warn(data.name);
      console.warn(data);
      this.router.navigate(['confirm']);
      this.sharedService.setMessage(data.cik, data.ccc, data.amount, data.name, data.email, data.phone);
    } else {
      this.validateAllFormFields(this.formdata); //{7}
    }
  }
  onClickClear() {
    this.formHandler();
  }
  formHandler() {
    this.formdata = new FormGroup({
      cik: new FormControl(null, Validators.compose([Validators.required])),
      ccc: new FormControl(null, Validators.required),
      payAmount: new FormControl(null, Validators.required),
      name: new FormControl('', Validators.required),
      email: new FormControl(null, Validators.compose([Validators.email, Validators.required])),
      phone: new FormControl(null, Validators.compose([Validators.required, Validators.minLength(10)])),
    });
  }
  get cik() {
    return this.formdata.get('cik');
  }
  get ccc() {
    return this.formdata.get('ccc');
  }
  get payAmount() {
    return this.formdata.get('payAmount');
  }
  get name() {
    return this.formdata.get('name');
  }
  get email() {
    return this.formdata.get('email');
  }
  get phone() {
    return this.formdata.get('phone');
  }

  public inputValidato(event: any) {
    console.warn(event.target.value.length);
    const pattern = /^[ 0-9]*$/;
    //
    //let inputChar = String.fromCharCode(event.charCode)
    if (!pattern.test(event.target.value)) {
      event.target.value = event.target.value.replace(/[^0-9]/g, '');
      // invalid character, prevent input
    }
  }

  format() {
    if (this.mynumber != '') {
      this.mynumber = this.padLeft(this.mynumber, '0', 10);
    }
  }

  padLeft(text: string, padChar: string, size: number): string {
    return (String(padChar).repeat(size) + text).substr(size * -1, size);
  }

  validateAllFormFields(formGroup: FormGroup) {
    //{1}
    Object.keys(formGroup.controls).forEach(field => {
      //{2}
      const control = formGroup.get(field); //{3}
      if (control instanceof FormControl) {
        //{4}
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof FormGroup) {
        //{5}
        this.validateAllFormFields(control); //{6}
      }
    });
  }
}

/* eslint-enable */
