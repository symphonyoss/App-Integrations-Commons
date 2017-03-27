import {
  FETCH_USER_ID_SUCCESS,
} from '../actions';

const userId = (state = '', action) => {
  switch (action.type) {
    case FETCH_USER_ID_SUCCESS:
      return action.payload.toString();
    default:
      return state;
  }
};

export default userId;
