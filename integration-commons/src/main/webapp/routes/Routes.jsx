/* eslint-disable no-unused-vars */
import React from 'react';
import { Router, Route, hashHistory } from 'react-router';
import Home from '../views/Home';
import CreateView from '../views/CreateView';
import EditView from '../views/EditView';
import RemoveView from '../views/RemoveView';
import InstanceCreated from '../views/InstanceCreated';

const Routes = () => (
  <Router history={hashHistory}>
    <Route path='/' component={Home} />
    <Route path='/create-view' component={CreateView} />
    <Route path='/edit-view' component={EditView} />
    <Route path='/remove-view' component={RemoveView} />
    <Route path='/instance-created' component={InstanceCreated} />
  </Router>
);

export default Routes;
