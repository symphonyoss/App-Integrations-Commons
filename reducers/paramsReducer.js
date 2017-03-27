import {
  GET_APP_PARAMS,
} from '../actions';

const appParams = (state = {}, action) => {
  switch (action.type) {
    case GET_APP_PARAMS:
      return action.params;
    default:
      return state;
  }
};

export default appParams;
