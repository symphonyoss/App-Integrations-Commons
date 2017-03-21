/* eslint-disable no-unused-vars */
import { Utils } from '../js/utils.service';

/* Setup initial parameters */
export const FETCH_USER_ID = 'FETCH_USER_ID';
export const FETCH_USER_ID_SUCCESS = 'FETCH_USER_ID_SUCCESS';

/* Instance List */
export const FETCH_INSTANCE_LIST = 'FETCH_INSTANCE_LIST';
export const FETCH_INSTANCE_LIST_SUCCESS = 'FETCH_INSTANCE_LIST_SUCCESS';
export const FETCH_USER_ROOMS = 'FETCH_USER_ROOMS';
export const FETCH_USER_ROOMS_SUCCESS = 'FETCH_USER_ROOMS_SUCCESS';


/* Edit instance */
export const EDIT_INSTANCE = 'EDIT_INSTANCE';
export const EDIT_INSTANCE_SUCCESS = 'EDIT_INSTANCE_SUCCESS';

/* Create stream */
export const CREATE_STREAM = 'CREATE_STREAM';
export const SWITCH_STREAM_TYPE = 'SWITCH_STREAM_TYPE';

/* Create instance */
export const CHANGE_DESCRIPTION = 'CHANGE_DESCRIPTION';
export const CREATE_INTANCE = 'CREATE_INTANCE';
export const CREATE_INSTANCE_SUCCESS = 'CREATE_INSTANCE_SUCCESS';

/* General Errors */
export const ERROR = 'ERROR';

const configurationId = Utils.getParameterByName('configurationId');

const botUserId = Utils.getParameterByName('botUserId');

export function fetchUserId() {
  const extendedUserService = SYMPHONY.services.subscribe('extended-user-service');
  const promisedUserId = extendedUserService.getUserId();

  return {
    type: FETCH_USER_ID,
    payload: promisedUserId,
  };
}

export function fetchUserIdSuccess(user) {
  return {
    type: FETCH_USER_ID_SUCCESS,
    payload: user.payload,
  };
}

/*
  Actions for Table Instance component (List View)
*/
export function fetchInstanceList() {
  const integrationConfService = SYMPHONY.services.subscribe('integration-config');
  const promisedInstanceList = integrationConfService.getConfigurationInstanceList('578543c2e4b0edcf4f5ff520');
  return {
    type: FETCH_INSTANCE_LIST,
    payload: promisedInstanceList,
  };
}

export function fetchInstanceListSuccess(instanceList) {
  const response = Utils.normalizeInstanceList(instanceList.payload);
  return {
    type: FETCH_INSTANCE_LIST_SUCCESS,
    payload: response,
  };
}

export function fetchUserRooms() {
  const extendedUserService = SYMPHONY.services.subscribe('extended-user-service');
  const promisedRooms = extendedUserService.getRooms();
  return {
    type: FETCH_USER_ROOMS,
    payload: promisedRooms,
  };
}

export function fetchUserRoomsSuccess(userRooms) {
  const response = Utils.getUserRooms(userRooms.payload);
  return {
    type: FETCH_USER_ROOMS_SUCCESS,
    payload: response,
  };
}

/*
  Actions for Input Description component, Posting Location component and Suggestions Rooms
  component (Edit View)
*/
export function editInstanceById(configurationId, instanceId, payload) {
  /*
    TODO, make an ajax request to the end poit passing
    the configurationId, instanceId and instance payload parameters
  */
  const response = null;
  return {
    type: EDIT_INSTANCE,
    response,
  };
}

export function editInstanceByIdSuccess(instance) {
  return {
    type: EDIT_INSTANCE_SUCCESS,
    payload: instance,
  };
}

/*
  Creates the stream to be able to create an instance
*/
export function createStream(obj) {
  // if(obj.streamType === 'IM'){
  const streamService = SYMPHONY.services.subscribe('stream-service');
  const _streams = [];
  const promisedIM = streamService.createIM([botUserId]);

  return {
    type: CREATE_STREAM,
    payload: promisedIM,
  };
  // }
}

export function switchStreamType(streamType) {
  return {
    type: SWITCH_STREAM_TYPE,
    payload: streamType,
  };
}

export function changeDescription(description) {
  return {
    type: CHANGE_DESCRIPTION,
    payload: description,
  };
}
/*
  Actions for Input Description component,
  Posting Location component and Suggestions Rooms component (Create View)
*/
export function createInstance(streamId, description, userId) {
  const integrationConfService = SYMPHONY.services.subscribe('integration-config');
  const _streams = [];
  _streams.push(streamId);
  const optionalProperties = `{"owner":"${userId}","streams":["${streamId}"],"streamType":"IM"}`;
  const payload = {
    configurationId,
    description,
    creatorId: userId,
    optionalProperties,
  };
  const saveInstance = integrationConfService.createConfigurationInstance(configurationId, payload);

  return {
    type: CREATE_INTANCE,
    payload: saveInstance,
  };
}

/*
  The instance was create Successfully
*/
export function createInstanceSuccess(instance) {
  return {
    type: CREATE_INSTANCE_SUCCESS,
    payload: instance,
  };
}

export function setError(error) {
  return {
    type: ERROR,
    payload: error,
  };
}
