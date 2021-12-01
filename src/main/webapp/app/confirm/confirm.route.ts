import { Route } from '@angular/router';

import { ConfirmComponent } from './confirm.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: ConfirmComponent,
  data: {
    pageTitle: 'Welcome, Java Hipster!',
  },
};
