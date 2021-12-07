import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaynow } from '../paynow.model';
import { PaynowService } from '../service/paynow.service';
import { PaynowDeleteDialogComponent } from '../delete/paynow-delete-dialog.component';

@Component({
  selector: 'jhi-paynow',
  templateUrl: './paynow.component.html',
})
export class PaynowComponent implements OnInit {
  paynows?: IPaynow[];
  isLoading = false;

  constructor(protected paynowService: PaynowService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.paynowService.query().subscribe(
      (res: HttpResponse<IPaynow[]>) => {
        this.isLoading = false;
        this.paynows = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IPaynow): number {
    return item.id!;
  }

  delete(paynow: IPaynow): void {
    const modalRef = this.modalService.open(PaynowDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.paynow = paynow;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
