import { combineReducers } from 'redux';
import userId from './userReducer';
import instanceList from './instancesListReducer';
import userRooms from './roomsReducer';
import appName from './appNameReducer';
import baseWebHookURL from './webHookURLReducer';
import instance from './instanceReducer';

const rootReducer = combineReducers({
  appName,
  baseWebHookURL,
  userId,
  userRooms,
  instanceList,
  instance,
});

export default rootReducer;
