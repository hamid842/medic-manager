import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import MedicineInfo from './medicine-info';
import MedicineInfoDetail from './medicine-info-detail';
import MedicineInfoUpdate from './medicine-info-update';
import MedicineInfoDeleteDialog from './medicine-info-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={MedicineInfoDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={MedicineInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={MedicineInfoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={MedicineInfoDetail} />
      <ErrorBoundaryRoute path={match.url} component={MedicineInfo} />
    </Switch>
  </>
);

export default Routes;
