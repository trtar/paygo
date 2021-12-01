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
  selector: 'jhi-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss'],
})
export class ConfirmComponent implements OnInit, OnDestroy {
  account: Account | null = null;
  message!: any[];
  formdata: any;
  cik: any;
  ccc: any;
  payAmount: any;
  name: any;
  email: any;
  phone: any;

  private readonly destroy$ = new Subject<void>();

  constructor(private accountService: AccountService, private router: Router, private sharedService: SharedConfirmService) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.message = this.sharedService.getMessage();
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
