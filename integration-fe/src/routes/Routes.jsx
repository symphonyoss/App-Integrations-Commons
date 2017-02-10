import React from 'react';
import { Router, Route, hashHistory } from 'react-router';
import Home from '../views/Home';
import CreateView from '../views/CreateView';

const Routes = () => (
  <Router history={hashHistory}>
    <Route path='/' component={Home} />
    <Route path='/create-view' component={CreateView} />
  </Router>
);

export default Routes;
