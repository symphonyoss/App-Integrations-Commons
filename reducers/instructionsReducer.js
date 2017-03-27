import {
  GET_APP_INSTRUCTIONS,
} from '../actions';

const appInstructions = (state = {}, action) => {
  switch (action.type) {
    case GET_APP_INSTRUCTIONS:
      return action.instructions;
    default:
      return state;
  }
};

export default appInstructions;
