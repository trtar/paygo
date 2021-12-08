import { CommonModule } from '@angular/common';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResponseComponent } from './response.component';
import { ResponseRoute } from './response.route';

@NgModule({
  imports: [RouterModule.forRoot([ResponseRoute], { useHash: true }), CommonModule],
  declarations: [],
  entryComponents: [],
  providers: [],
})
export class ResponseModule {}
