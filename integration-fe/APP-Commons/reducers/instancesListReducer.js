import {
  FETCH_INSTANCE_LIST_SUCCESS,
  SUCCESSFULLY_CREATED,
  SUBMIT_DONE,
} from '../actions';

const instanceList = (state = { instances: [], loading: true }, action) => {
  switch (action.type) {
    case FETCH_INSTANCE_LIST_SUCCESS:
      return Object.assign({}, state, {
        ...state,
        instances: action.payload,
        loading: false,
      });
    case SUCCESSFULLY_CREATED:
      return Object.assign({}, state, {
        ...state,
        loading: false,
        instances: [
          ...state.instances,
          action.instance,
        ],
      });
    case SUBMIT_DONE:
      return Object.assign({}, state, {
        ...state,
        loading: true,
        instances: [],
      });
    default:
      return state;
  }
};

export default instanceList;
