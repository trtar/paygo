import { CommonModule } from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ConfirmComponent } from './confirm.component';
import { ConfirmRoute } from './confirm.route';

@NgModule({
  imports: [RouterModule.forRoot([ConfirmRoute], { useHash: true }), CommonModule],
  declarations: [],
  entryComponents: [],
  providers: [],
})
export class ConfirmModule {}
