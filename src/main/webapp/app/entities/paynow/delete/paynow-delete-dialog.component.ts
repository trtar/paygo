import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaynow } from '../paynow.model';
import { PaynowService } from '../service/paynow.service';

@Component({
  templateUrl: './paynow-delete-dialog.component.html',
})
export class PaynowDeleteDialogComponent {
  paynow?: IPaynow;

  constructor(protected paynowService: PaynowService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.paynowService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
