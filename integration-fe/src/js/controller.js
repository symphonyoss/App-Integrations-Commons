import { Utils } from './utils.service';
import config from './config.service';

const params = {
  appId: Utils.getParameterByName('id'),
  configurationId: Utils.getParameterByName('configurationId'),
  botUserId: Utils.getParameterByName('botUserId'),
  context: Utils.getParameterByName('context') ? `/${Utils.getParameterByName('context')}` : '',
  host: `${window.location.protocol}//${window.location.hostname}:${window.location.port}`,
};

const dependencies = [
  'ui',
  'modules',
  'applications-nav',
  'account',
  'integrationConfigService',
  'stream-service',
  'integration-config',
];

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
        { title: config.app_title },
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

SYMPHONY.remote.hello().then(helloController);
