import { Route } from '@angular/router';

import { ResponseComponent } from './response.component';

export const ResponseRoute: Route = {
  path: '',
  component: ResponseComponent,
  outlet: 'response',
};
