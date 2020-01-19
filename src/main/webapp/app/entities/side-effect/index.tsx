import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SideEffect from './side-effect';
import SideEffectDetail from './side-effect-detail';
import SideEffectUpdate from './side-effect-update';
import SideEffectDeleteDialog from './side-effect-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SideEffectDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SideEffectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SideEffectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SideEffectDetail} />
      <ErrorBoundaryRoute path={match.url} component={SideEffect} />
    </Switch>
  </>
);

export default Routes;
