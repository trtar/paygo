import { Route } from '@angular/router';

import { ConfirmComponent } from './confirm.component';

export const ConfirmRoute: Route = {
  path: '',
  component: ConfirmComponent,
  outlet: 'confirm',
};
