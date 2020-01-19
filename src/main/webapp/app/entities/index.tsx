import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicineInfo from './medicine-info';
import SideEffect from './side-effect';
import TimeTable from './time-table';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}medicine-info`} component={MedicineInfo} />
      <ErrorBoundaryRoute path={`${match.url}side-effect`} component={SideEffect} />
      <ErrorBoundaryRoute path={`${match.url}time-table`} component={TimeTable} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
