import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE]), ReactiveFormsModule],
  declarations: [],
})
export class HomeModule {}
