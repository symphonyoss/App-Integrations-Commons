/* eslint-disable no-unused-vars */
/* eslint-disable no-debugger */
// import Config from '../js/Config.service';
import Config from '../js/config.service';

// Custom actions from boilerplate
export const INTEGRATION_LOGO = Config.getParams.appLogo;
export const INTEGRATION_TITLE = Config.getParams.appTitle;
export const INTEGRATION_SUBTITLE = Config.getParams.appSubTitle;
export const INTEGRATION_NAME = Config.getParams.appName;
export const INTEGRATION_IM_SHORTHAND = Config.getParams.appIMShortHand;
export const INTEGRATION_TOGGLE_SETUP_INSTRUCTIONS = Config.getParams.appToggleSetupInstructions;

// General actions
export const ADD_MEMBER_SHIP_FAILED = 'ADD_MEMBER_SHIP_FAILED';
export const ADD_STREAM_TO_INSTANCE = 'ADD_STREAM_TO_INSTANCE';
export const CALL_SAVE_INSTANCE = 'CALL_SAVE_INSTANCE';
export const CHANGE_INSTANCE_NAME = 'CHANGE_INSTANCE_NAME';
export const CHANGE_STREAM_TYPE = 'CHANGE_STREAM_TYPE';
export const CREATE_IM_FAILED = 'CREATE_IM_FAILED';
export const EDIT_INSTANCE = 'EDIT_INSTANCE';
export const GET_APP_PARAMS = 'GET_APP_PARAMS';
export const GET_APP_INSTRUCTIONS = 'GET_APP_INSTRUCTIONS';
export const HIDE_REQUIRE_NAME = 'HIDE_REQUIRE_NAME';
export const HIDE_REQUIRE_ROOMS = 'HIDE_REQUIRE_ROOMS';
export const FAILED_OPERATION = 'FAILED_OPERATION';
export const FETCH_FAILED = 'FETCH_FAILED';
export const FETCH_INSTANCE_LIST = 'FETCH_INSTANCE_LIST';
export const FETCH_INSTANCE_LIST_SUCCESS = 'FETCH_INSTANCE_LIST_SUCCESS';
export const FETCH_ROOMS_SUCCESS = 'FETCH_ROOMS_SUCCESS';
export const FETCH_USER_ID_SUCCESS = 'FETCH_USER_ID_SUCCESS';
export const GET_ACTIVE_INSTANCE_RESETED = 'GET_ACTIVE_INSTANCE_RESETED';
export const GET_INSTANCE_INFO = 'GET_INSTANCE_INFO';
export const MESSAGE_ERROR = 'MESSAGE_ERROR';
export const REQUIRED_NAME = 'REQUIRED_NAME';
export const REQUIRED_ROOMS = 'REQUIRED_ROOMS';
export const REMOVE_INSTANCE = 'REMOVE_INSTANCE';
export const REMOVE_STREAM_FROM_INSTANCE = 'REMOVE_STREAM_FROM_INSTANCE';
export const RESET_MESSAGE = 'RESET_MESSAGE';
export const RESET_POSTING_LOCATION_ROOMS = 'RESET_POSTING_LOCATION_ROOMS';
export const SAVE_INSTANCE = 'SAVE_INSTANCE';
export const SAVE_INSTANCE_FAILED = 'SAVE_INSTANCE_FAILED';
export const SET_APP_NAME = 'SET_APP_NAME';
export const SUBMIT_DONE = 'SUBMIT_DONE';
export const SUCCESSFULLY_CREATED = 'SUCCESSFULLY_CREATED';
export const SUCCESSFULLY_REMOVED = 'SUCCESSFULLY_REMOVED';
export const SUCCESSFULLY_UPDATED = 'SUCCESSFULLY_UPDATED';

// Message Actions
export const FAILED_OPERATION_MESSAGE = 'An error has ocurred and the operation could not be completed. Please try again later.';
export const NAME_IS_REQUIRED = 'Name is required!';
export const ROOMS_IS_REQUIRED = 'Posting Location is required!';
export const SUCCESSFULLY_CREATED_MESSAGE = 'You have successfully Configured a new #INTEGRATION_NAME integration. Register your Configured integration on #INTEGRATION_NAME to complete setup.';
export const SUCCESSFULLY_REMOVED_MESSAGE = 'Instance removed from Symphony. You can now remove the webhook from #INTEGRATION_NAME';
export const SUCCESSFULLY_UPDATED_MESSAGE = 'You have successfully updated your #INTEGRATION_NAME integration.';

// Other constants
export const operations = {
  CREATE: 'CREATE',
  UPDATE: 'UPDATE',
  REMOVE: 'REMOVE',
};

// Actions creators
export const changeInstanceName = name => ({
  type: CHANGE_INSTANCE_NAME,
  name,
});

export const changeStreamType = streamType => ({
  type: CHANGE_STREAM_TYPE,
  streamType,
});

export const addStreamToInstance = room => ({
  type: ADD_STREAM_TO_INSTANCE,
  room,
});

export const removeStreamFromInstance = room => ({
  type: REMOVE_STREAM_FROM_INSTANCE,
  room,
});

export const saveInstance = () => ({
  type: SAVE_INSTANCE,
});

export const editInstance = instance => ({
  type: EDIT_INSTANCE,
  instance,
});

export const removeInstance = instance => ({
  type: REMOVE_INSTANCE,
  instance,
});

export const getInstanceInfo = instance => ({
  type: GET_INSTANCE_INFO,
  instance,
});

export const resetPostingLocationRooms = () => ({
  type: RESET_POSTING_LOCATION_ROOMS,
});

export const submitDone = () => ({
  type: SUBMIT_DONE,
});

export const getInstanceList = () => ({
  type: FETCH_INSTANCE_LIST,
});

export const showRequireName = state => ({
  type: REQUIRED_NAME,
});

export const hideRequireName = state => ({
  type: HIDE_REQUIRE_NAME,
});

export const hideRequireRooms = state => ({
  type: HIDE_REQUIRE_ROOMS,
});

export const showRequireRooms = () => ({
  type: REQUIRED_ROOMS,
});

export const resetMessage = () => ({
  type: RESET_MESSAGE,
});

export const getAppParams = () => ({
  type: GET_APP_PARAMS,
});
