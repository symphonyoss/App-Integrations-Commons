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
} from '../actions/actions';

const INITIAL_STATE = {
  instances: [],
  loading: true,
  erro: null,
  activeInstance: null,
  configurationId: null,
};

export default function (state = INITIAL_STATE, action) {
  let error;

  switch (action.type) {
    case FETCH_INSTANCE_LIST_SUCCESS:
      return Object.assign({}, state, {
        ...state,
        instances: [
          ...state.instances,
          action.payload
        ],
        loading: false,
      });
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
