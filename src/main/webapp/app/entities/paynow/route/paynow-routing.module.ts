import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaynowComponent } from '../list/paynow.component';
import { PaynowDetailComponent } from '../detail/paynow-detail.component';
import { PaynowUpdateComponent } from '../update/paynow-update.component';
import { PaynowRoutingResolveService } from './paynow-routing-resolve.service';

const paynowRoute: Routes = [
  {
    path: '',
    component: PaynowComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaynowDetailComponent,
    resolve: {
      paynow: PaynowRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaynowUpdateComponent,
    resolve: {
      paynow: PaynowRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaynowUpdateComponent,
    resolve: {
      paynow: PaynowRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paynowRoute)],
  exports: [RouterModule],
})
export class PaynowRoutingModule {}
