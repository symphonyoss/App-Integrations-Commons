import {
  FETCH_FAILED,
  SAVE_INSTANCE_FAILED,
  ADD_MEMBER_SHIP_FAILED,
  CREATE_IM_FAILED,
  MESSAGE_ERROR,
} from '../actions';

const appError = (state = {}, action) => {
  switch (action.type) {
    case FETCH_FAILED:
    case SAVE_INSTANCE_FAILED:
    case ADD_MEMBER_SHIP_FAILED:
    case CREATE_IM_FAILED:
    case MESSAGE_ERROR:
      return action.error;
    default:
      return state;
  }
};

export default appError;
