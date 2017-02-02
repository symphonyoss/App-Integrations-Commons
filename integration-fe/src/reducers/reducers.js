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

const INITIAL_STATE =  {
  instances: [],
  loading: true,
  erro: null,
  activeInstance: null,
};

export default function (state = INITIAL_STATE, action) {
  let error;

  switch(action.type) {
    case FETCH_INSTANCE_LIST:
      return {
        ...state,
        instances: [],
        loading: true,
        error: null,
      };
    case FETCH_INSTANCE_LIST_SUCCESS:
      return {
        ...state,
        instances: action.payload,
        loading: false,
        error: null,
      };
    case FETCH_INSTANCE_LIST_ERROR:
      error = action.payload || {message: action.payload.message} // 2nd one is network or server down errors
      return {
        ...state,
        instances: null,
        loading: false,
        error: error,
      };
    case CREATE_STREAM:
      return {
        ...state,
          loading: true,
          error: null
      } ; 
    case CREATE_INSTANCE_SUCCESS:
      return {
        ...state,
        instances: [...instances, action.payload],
        loading: false,
        error: null,
      };
    default:
      return state;
  }
}
