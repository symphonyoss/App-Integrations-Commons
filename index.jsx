/* eslint-disable no-unused-vars */
import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { Utils } from './js/utils.service';
import configureStore from './store/configureStore';
import factory from './js/config.service';
import './vendors/font-awesome-4.6.3/css/font-awesome.min.css';

// Components
import MessageBoxComponent from './components/MessageBox/MessageBox';
import IntegrationHeaderComponent from './components/IntegrationHeader/IntegrationHeader';
import ConfigureNewComponent from './components/ConfigureNew/ConfigureNew';
import TableInstanceComponent from './components/TableInstance/TableInstanceContainer';
// Views
import HomeScreen from './views/Home';
import CreateScreen from './views/CreateView';
import EditScreen from './views/EditView';
import InstanceCreatedScreen from './views/InstanceCreated';
import RemoveViewScreen from './views/RemoveView';
// Export Components
export const MessageBox = MessageBoxComponent;
export const IntegrationHeader = IntegrationHeaderComponent;
export const ConfigureNew = ConfigureNewComponent;
export const TableInstance = TableInstanceComponent;
// Export Views
export const Home = HomeScreen;
export const CreateView = CreateScreen;
export const EditView = EditScreen;
export const InstanceCreated = InstanceCreatedScreen;
export const RemoveView = RemoveViewScreen;

const params = {
  appId: Utils.getParameterByName('id'),
  configurationId: Utils.getParameterByName('configurationId'),
  botUserId: Utils.getParameterByName('botUserId'),
  context: Utils.getParameterByName('context') ? `/${Utils.getParameterByName('context')}` : '',
  host: `${window.location.protocol}//${window.location.hostname}:${window.location.port}`,
};

const dependencies = [
  'ui',
  'extended-user-service',
  'modules',
  'applications-nav',
  'account',
  'stream-service',
  'integration-config',
];

/*
* register                                  register application on symphony client
* @params       services                    additional services for each integration. (Optional)
* @return       SYMPHONY.remote.hello       returns a SYMPHONY remote hello service.
*/
export const register = (SYMPHONY, services) => {
  /* if (services) {
    const newDependencies = services.filter(service => service !== dependencies.map(item => item));
    dependencies.concat(newDependencies);
    debugger;
  }*/

  // create our own service
  const listService = SYMPHONY.services.register(`${params.appId}:controller`);
  function registerApplication() {
    // system services
    const uiService = SYMPHONY.services.subscribe('ui');
    const modulesService = SYMPHONY.services.subscribe('modules');

    uiService.registerExtension(
      'app-settings',
      params.appId,
      `${params.appId}:controller`,
      { label: 'Configure' }
    );

    listService.implement({
      trigger() {
        const url = [
          `${params.host + params.context}/app.html`,
          `?configurationId=${params.configurationId}`,
          `&botUserId=${params.botUserId}`,
          `&id=${params.appId}`,
        ];

        // invoke the module service to show our own application in the grid
        modulesService.show(
          params.appId,
          { title: factory.getParams.appTitle },
          `${params.appId}:controller`,
          url.join(''),
          { canFloat: true },
        );
      },
    });
  }

  function helloController() {
    SYMPHONY.application.register(
      params.appId,
      dependencies,
      [`${params.appId}:controller`]
    ).then(registerApplication);
  }
  return SYMPHONY.remote.hello().then(helloController);
};

/*
* connect         connects the application on symphony client
* @param          SYMPHONY          Global SYMPHONY object (Required)
* @param          config            custom parameters for each integration. (Required)
* @param          Routes            default, or custom, routes file (Required)
* @param          elem              HTML DOM element where to render the configurator (Required)
* @param          Instructions      react dom for custom setup instructions (Optional)
*/
export const connect = (SYMPHONY, config, Routes, elem, Instructions) => {
  function loadApplication() {
    factory.setParams(config);
    factory.setInstructions(Instructions);
    const store = configureStore();
    render(
      <Provider store={store}>
        <Routes />
      </Provider>,
      elem
    );
  }

  let themeColor,
    themeSize;

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
  return SYMPHONY.remote.hello().then(helloApplication);
};
/* import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
// import Routes from './routes/Routes';
import configureStore from './store/configureStore';
import config from './js/config.service';
// Components
import MessageBoxComponent from './components/MessageBox/MessageBox';
import IntegrationHeaderComponent from './components/IntegrationHeader/IntegrationHeader';
// import SetupInstructionsComponent from './components/SetupInstructions/SetupInstructions';
import ConfigureNewComponent from './components/ConfigureNew/ConfigureNew';
import TableInstanceComponent from './components/TableInstance/TableInstanceContainer';
// Views
import HomeScreen from './views/Home';
import CreateScreen from './views/CreateView';
import EditScreen from './views/EditView';
import InstanceCreatedScreen from './views/InstanceCreated';
import RemoveViewScreen from './views/RemoveView';

// Export Views
export const Home = HomeScreen;
export const CreateView = CreateScreen;
export const EditView = EditScreen;
export const InstanceCreated = InstanceCreatedScreen;
export const RemoveView = RemoveViewScreen;

// Export Components
export const MessageBox = MessageBoxComponent;
export const IntegrationHeader = IntegrationHeaderComponent;
// export const SetupInstructions = SetupInstructionsComponent;
export const ConfigureNew = ConfigureNewComponent;
export const TableInstance = TableInstanceComponent;

export default (params, Routes, Instructions, elem) => {
  config.setParams(params);
  config.setInstructions(Instructions);
  const store = configureStore();
  render(
    <Provider store={store}>
      <Routes />
    </Provider>,
    elem
  );
};*/
