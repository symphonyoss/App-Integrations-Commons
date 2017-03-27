import { combineReducers } from 'redux';
import userId from './userReducer';
import instanceList from './instancesListReducer';
import userRooms from './roomsReducer';
import appParams from './paramsReducer';
import appInstructions from './instructionsReducer';
import instance from './instanceReducer';
import message from './messagesReducer';
import error from './errorReducer';

const rootReducer = combineReducers({
  appParams,
  appInstructions,
  userId,
  userRooms,
  instanceList,
  instance,
  message,
  error,
});

export default rootReducer;
