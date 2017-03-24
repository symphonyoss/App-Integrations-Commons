/* eslint-disable no-debugger */
import React from 'react';
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
};
