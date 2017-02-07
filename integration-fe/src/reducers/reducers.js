import { Utils } from '../js/utils.service';

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
  ERROR,
} from '../actions/actions';

const INITIAL_STATE = {
  instances: [],
  loading: false,
  error: null,
  activeInstance: null,
  userRooms: [],
  userId: null,
  configurationId: Utils.getParameterByName('configurationId'),
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
        loading: false,
        error: null,
        userId: action.payload,
      })
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
      })
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
          action.payload
        ],
        activeInstance: action.payload,
        loading: false,
      });
    case ERROR:
      error = action.payload || { message: action.payload.message } // 2nd one is network or server down errors
      return Object.assign({}, state, {
        ...state,
        instances: null,
        loading: false,
        error: error,
      });
    default:
      return state;
  }
}

export default integrationApp;
