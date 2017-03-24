import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import Routes from '../routes/Routes';
import configureStore from '../store/configureStore';
import { Utils } from './utils.service';
import '../../vendors/font-awesome-4.6.3/css/font-awesome.min.css';

const params = {
  appId: Utils.getParameterByName('id'),
  configurationId: Utils.getParameterByName('configurationId'),
};

const dependencies = [
  'ui',
  'modules',
  'applications-nav',
  'integration-config',
  'extended-user-service',
  'stream-service',
  'integration-config',
];

let themeColor;
let themeSize;

function loadApplication() {
  const store = configureStore();
  render(
    <Provider store={store}>
      <Routes />
    </Provider>,
    document.getElementById('app')
  );
}

function connectApplication() {
  const uiService = SYMPHONY.services.subscribe('ui');
  loadApplication();

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
    params.appId,
    dependencies,
    [`${params.appId}:app`]
  ).then(connectApplication);
}

SYMPHONY.remote.hello().then(helloApplication);
