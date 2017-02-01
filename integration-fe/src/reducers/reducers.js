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
} from '../actions/actions';

const INITIAL_STATE =  {
  instanceList: {
    instances: [],
    loading: false,
    error: null,
  },
  newInstance: {
    instance: null,
    loading: false,
    error: null,
  },
  activeInstance: {
    instance: null,
    loading: false,
    error: null,
  },
  deletedInstance: {
    instance: null,
    loading: false,
    error: null,
  },
};

export default function (state = INITIAL_STATE, action) {
  let error;

  switch(action.type) {
    case FETCH_INSTANCE_LIST:
      return {
        ...state,
        instanceList: {
          instances: [],
          loading: true,
          error: null,
        },
      };
    case FETCH_INSTANCE_LIST_SUCCESS:
      return {
        ...state,
        instanceList: {
          instances: action.payload,
          loading: false,
          error: null,
        }
      };
    case FETCH_INSTANCE_LIST_ERROR:
      error = action.payload || {message: action.payload.message} // 2nd one is network or server down errors
      return {
        ...state,
        instanceList: {
          instances: [],
          loading: false,
          error: error,
        }
      };
  }
}
