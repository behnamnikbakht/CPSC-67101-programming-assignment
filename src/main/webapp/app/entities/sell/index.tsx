import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Sell from './sell';
import SellDetail from './sell-detail';
import SellUpdate from './sell-update';
import SellDeleteDialog from './sell-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SellUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SellUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SellDetail} />
      <ErrorBoundaryRoute path={match.url} component={Sell} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SellDeleteDialog} />
  </>
);

export default Routes;
