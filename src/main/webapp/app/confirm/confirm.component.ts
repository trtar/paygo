import { Component, OnInit, OnDestroy, Input, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import { FormGroup, FormControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DOCUMENT } from '@angular/common';
import { SharedConfirmService } from 'app/shared/sharedconfirm.service';

//import { AppointmentService } from './appointment.service';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Confirm } from './confirm';
import { ConfirmService } from './confirm.service';

/* eslint-disable */

@Component({
  selector: 'jhi-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss'],
})
export class ConfirmComponent implements OnInit, OnDestroy {
  appointment: Confirm = new Confirm();
  account: Account | null = null;
  message!: any[];
  formdata: any;
  cik: any;
  ccc: any;
  paymount: any;
  name: any;
  email: any;
  phone: any;
  closeResult = 'close';
  reDirection: any;

  public counter: number = 10;

  private readonly destroy$ = new Subject<void>();

  constructor(
    private accountService: AccountService,
    private router: Router,
    private confirmService: ConfirmService,
    private sharedService: SharedConfirmService,
    private modalService: NgbModal,
    @Inject(DOCUMENT) private document: Document
  ) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => (this.account = account));
    this.message = this.sharedService.getMessage();
    this.test();
  }

  open(content: any) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
      result => {
        this.closeResult = `Closed with: ${result}`;
      },
      reason => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      }
    );
    this.startCountDown();
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  onClickSubmit(data: any) {
    console.warn(data.name);
  }
  onClickContinue() {
    this.goToUrl();
  }
  onClickCancel() {}
  onClickClear() {
    this.formdata = new FormGroup({
      cik: new FormControl(),
      ccc: new FormControl(),
      paymount: new FormControl(),
      name: new FormControl(''),
      email: new FormControl(''),
      phone: new FormControl(),
    });
  }
  startCountDown() {
    if (this.counter > 0) {
      this.doCount();
    } else {
      console.warn('counter is: ', this.counter);
    }
  }
  doCount() {
    setTimeout(() => {
      this.counter = this.counter - 1;
      this.processCount();
    }, 1000);
  }
  processCount() {
    console.log('counter is: ', this.counter);
    console.warn('counter is: ', this.counter);
    if (this.counter == 0) {
      //this.router.navigate(['/to-be-redirected']);
      this.goToUrl();
      this.sharedService.getRedirection();
      console.warn('--counter end--');
      //   this.counter = 10;
    } else {
      this.doCount();
    }
  }
  goToUrl(): void {
    this.document.location.href = 'http://localhost:8080/to-be-redirected';
  }
  test() {
    this.appointment.cik = '111111111';
    this.appointment.ccc = '444444444444';
    this.appointment.name = 'tariku';
    this.appointment.email = '123';
    this.appointment.phone = '2345';
    console.warn(this.appointment);
    this.saveAppointment();
  }
  saveAppointment() {
    this.confirmService.createAppointment(this.appointment).subscribe(
      data => {
        console.warn('+++++++++++');
        console.log(data);
        console.warn(data);
      },
      error => console.log(error)
    );
  }
}
/* eslint-enable */
