import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormGroup, FormControl } from '@angular/forms';
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
  account: Account | null = null;
  formdata: any;
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
    console.warn(data.name);
    console.warn(data);
    this.router.navigate(['confirm']);
    this.sharedService.setMessage(data.cik, data.ccc, data.amount, data.name, data.email, data.phone);
  }
  onClickClear() {
    this.formdata = new FormGroup({
      cik: new FormControl(),
      ccc: new FormControl(),
      payAmount: new FormControl(),
      name: new FormControl(''),
      email: new FormControl(''),
      phone: new FormControl(),
    });
  }
}
/* eslint-enable */
