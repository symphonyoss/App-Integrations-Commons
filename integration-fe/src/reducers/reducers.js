/* eslint-disable no-unused-vars */
import { Utils } from '../js/utils.service';
import config from '../js/config.service';

import {
  FETCH_USER_ID,
  FETCH_USER_ID_SUCCESS,
  FETCH_INSTANCE_LIST,
  FETCH_INSTANCE_LIST_SUCCESS,
  FETCH_USER_ROOMS_SUCCESS,
  EDIT_INSTANCE,
  EDIT_INSTANCE_SUCCESS,
  CREATE_INTANCE,
  CREATE_INSTANCE_SUCCESS,
  CREATE_STREAM,
  CREATE_STREAM_SUCCESS,
  SWITCH_STREAM_TYPE,
  CHANGE_DESCRIPTION,
  ERROR,
} from '../actions/actions';

const configurationId = Utils.getParameterByName('configurationId');
const appId = Utils.getParameterByName('id');
const baseUrl = `${window.location.protocol}//${window.location.hostname}/integration`;
const baseWebhookUrl = `${baseUrl}/v1/whi/${appId}/${configurationId}`;
const messages = {
  loadingInstances: 'Searching for Instances',
};

const INITIAL_STATE = {
  instances: [],
  description: '',
  loading: true,
  error: null,
  activeInstance: null,
  streamType: 'IM',
  appName: config.app_name,
  userRooms: [],
  userId: null,
  baseWebhookUrl,
  appId,
  configurationId,
  messages,
};

const integrationApp = (state = INITIAL_STATE, action) => {
  let error;

  switch (action.type) {
    case FETCH_USER_ID:
      return Object.assign({}, state, {
        ...state,
        loading: true,
      });
    case FETCH_USER_ID_SUCCESS:
      return Object.assign({}, state, {
        ...state,
        error: null,
        userId: action.payload,
      });
    case FETCH_INSTANCE_LIST: {
      return Object.assign({}, state, {
        ...state,
        loading: true,
        error: null,
        activeInstance: null,
      });
    }
    case FETCH_INSTANCE_LIST_SUCCESS:
      return Object.assign({}, state, {
        ...state,
        instances: action.payload.slice(),
        loading: false,
        error: null,
      });
    case FETCH_USER_ROOMS_SUCCESS: {
      return Object.assign({}, state, {
        ...state,
        userRooms: action.payload.slice(),
      });
    }
    case CREATE_STREAM: // this new stream will be used to create the new instance...
      return Object.assign({}, state, {
        ...state,
        loading: true,
      });
    case CREATE_INSTANCE_SUCCESS:
      return Object.assign({}, state, {
        ...state,
        instances: [
          ...state.instances,
          action.payload,
        ],
        activeInstance: action.payload,
        loading: false,
      });
    case SWITCH_STREAM_TYPE:
      return Object.assign({}, state, {
        ...state,
        streamType: action.payload,
      });
    case CHANGE_DESCRIPTION:
      return Object.assign({}, state, {
        ...state,
        description: action.payload,
      });
    case ERROR:
      // 2nd one is network or server down errors
      error = action.payload || { message: action.payload.message };
      return Object.assign({}, state, {
        ...state,
        instances: null,
        loading: false,
        error,
      });
    default:
      return state;
  }
};

export default integrationApp;
