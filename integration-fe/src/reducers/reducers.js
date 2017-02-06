import { getParameterByName } from '../js/utils.service';

import {
  FETCH_INSTANCE_LIST,
  FETCH_INSTANCE_LIST_SUCCESS,
  FETCH_INSTANCE_LIST_ERROR,
  EDIT_INSTANCE,
  EDIT_INSTANCE_SUCCESS,
  EDIT_INSTANCE_ERROR,
  CREATE_INTANCE,
  CREATE_INSTANCE_SUCCESS,
  CREATE_INSTANCE_ERROR,
  CREATE_STREAM,
  CREATE_STREAM_SUCCESS,
  CREATE_STREAM_ERROR,
  CHANGE,
} from '../actions/actions';

const INITIAL_STATE = {
  instances: [],
  loading: true,
  error: null,
  activeInstance: null,
  configurationId: getParameterByName('configurationId'),
  text: '',
};

const integrationApp = (state = INITIAL_STATE, action) => {
  let error;

  switch (action.type) {
    case FETCH_INSTANCE_LIST: {
      return Object.assign({}, state, {
        ...state,
        loading: true,
        error: null,
        activeInstance: null,
      });
    }
    case FETCH_INSTANCE_LIST_SUCCESS:
      debugger;
      return Object.assign({}, state, {
        ...state,
        instances: action.payload.slice(),
        loading: false,
        error: null,
      });
    case CHANGE:
      debugger;
      return Object.assign({}, state, {
        ...state,
        text: action.payload
      });
    case CREATE_STREAM: // this new stream will be used to create the new instance...
      console.error('CREATE_STREAM');
      return Object.assign({}, state, {
        ...state,
        loading: true,
      });
    case CREATE_INSTANCE_SUCCESS:
      console.error('CREATE_INSTANCE_SUCCESS');
      return Object.assign({}, state, {
        ...state,
        instances: [
          ...state.instances,
          action.payload
        ],
        activeInstance: action.payload,
        loading: false,
      });
    case FETCH_INSTANCE_LIST_ERROR:
    case CREATE_STREAM_ERROR:
    case CREATE_INSTANCE_ERROR:
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
