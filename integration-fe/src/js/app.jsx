/* eslint-disable no-unused-vars */
import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import { Link, Router, Route, hashHistory, IndexRoute } from 'react-router';
import reducers from '../reducers/reducers';
import { getParameterByName } from './utils.service';
import Home from '../views/Home';

let store = createStore(reducers);

const appId = getParameterByName('id');

const dependencies = [
  'ui',
  'modules',
  'applications-nav',
  'extended-user-service',
];

let themeColor;
let themeSize;

function loadApplication(rooms) {
  document.getElementsByTagName('h1')[0].innerHTML = 'My Configurator';

  render(
    <Provider store={store}>
      <Home />
    </Provider>,
    document.getElementById('app')
  );
}

function connectApplication(response) {
  const userId = response.userReferenceId;

  // Subscribe to Symphony's services
  const modulesService = SYMPHONY.services.subscribe('modules');
  const navService = SYMPHONY.services.subscribe('applications-nav');
  const uiService = SYMPHONY.services.subscribe('ui');
  const shareService = SYMPHONY.services.subscribe('share');
  const extendedUserService = SYMPHONY.services.subscribe('extended-user-service');

  const promisedRooms = extendedUserService.invoke('getRooms');

  promisedRooms.then(loadApplication);

  // UI: Listen for theme change events
  uiService.listen('themeChangeV2', () => {
    SYMPHONY.remote.hello().then((resp) => {
      themeColor = resp.themeV2.name;
      themeSize = resp.themeV2.size;
      document.body.className = `symphony-external-app ${themeColor} ${themeSize}`;
    });
  });
}

function helloApplication(data) {
  themeColor = data.themeV2.name;
  themeSize = data.themeV2.size;

  // You must add the symphony-external-app class to the body element
  document.body.className = `symphony-external-app ${themeColor} ${themeSize}`;

  SYMPHONY.application.connect(
    appId,
    dependencies,
    [`${appId}:app`]
  ).then(connectApplication);
}

SYMPHONY.remote.hello().then(helloApplication);
