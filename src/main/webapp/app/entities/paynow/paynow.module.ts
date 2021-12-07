import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaynowComponent } from './list/paynow.component';
import { PaynowDetailComponent } from './detail/paynow-detail.component';
import { PaynowUpdateComponent } from './update/paynow-update.component';
import { PaynowDeleteDialogComponent } from './delete/paynow-delete-dialog.component';
import { PaynowRoutingModule } from './route/paynow-routing.module';

@NgModule({
  imports: [SharedModule, PaynowRoutingModule],
  declarations: [PaynowComponent, PaynowDetailComponent, PaynowUpdateComponent, PaynowDeleteDialogComponent],
  entryComponents: [PaynowDeleteDialogComponent],
})
export class PaynowModule {}
