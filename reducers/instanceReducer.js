/* eslint-disable no-debugger */
import {
  ADD_STREAM_TO_INSTANCE,
  CALL_SAVE_INSTANCE,
  CHANGE_INSTANCE_NAME,
  CHANGE_STREAM_TYPE,
  FAILED_OPERATION,
  GET_ACTIVE_INSTANCE_RESETED,
  REMOVE_STREAM_FROM_INSTANCE,
  RESET_POSTING_LOCATION_ROOMS,
  GET_INSTANCE_INFO,
  SUCCESSFULLY_CREATED,
  SUCCESSFULLY_REMOVED,
  SUCCESSFULLY_UPDATED,
} from '../actions';

const instanceReducer = (state = { saved: false, loading: false }, action) => {
  switch (action.type) {
    case GET_ACTIVE_INSTANCE_RESETED:
      // debugger;
      return action.instance;
    case CHANGE_INSTANCE_NAME:
      return Object.assign({}, state, {
        ...state,
        name: action.name,
      });
    case CHANGE_STREAM_TYPE:
      return Object.assign({}, state, {
        ...state,
        streamType: action.streamType,
        postingLocationRooms: [],
        streams: [],
      });
    case ADD_STREAM_TO_INSTANCE:
      return Object.assign({}, state, {
        ...state,
        postingLocationRooms: state.postingLocationRooms.concat([action.room]),
        streams: state.streams.concat([action.room.threadId]),
      });
    case REMOVE_STREAM_FROM_INSTANCE:
      return Object.assign({}, state, {
        ...state,
        postingLocationRooms: state.postingLocationRooms.filter(
          room => room.threadId !== action.room.threadId),
        streams: state.streams.filter(item => item !== action.room.threadId),
      });
    case GET_INSTANCE_INFO:
      return Object.assign({}, state, {
        ...state,
        name: action.instance.name,
        baseWebHookURL: action.instance.baseWebHookURL,
        instanceId: action.instance.instanceId,
        streamType: action.instance.streamType,
        postingLocationRooms: action.instance.postingLocationRooms.slice(),
        lastPosted: action.instance.lastPosted,
      });
    case RESET_POSTING_LOCATION_ROOMS:
      return Object.assign({}, state, {
        ...state,
        postingLocationRooms: [],
      });
    case SUCCESSFULLY_CREATED:
      return Object.assign({}, state, {
        ...state,
        name: action.instance.name,
        creatorId: action.instance.creatorId,
        instanceId: action.instance.instanceId,
        streamType: action.instance.streamType,
        streams: action.instance.streams.slice(),
        lastPosted: action.instance.lastPosted,
        saved: true,
        loading: false,
      });
    case SUCCESSFULLY_UPDATED:
    case SUCCESSFULLY_REMOVED:
      return Object.assign({}, state, {
        ...state,
        saved: true,
        loading: false,
      });
    case CALL_SAVE_INSTANCE:
      debugger;
      return Object.assign({}, state, {
        ...state,
        loading: true,
      });
    case FAILED_OPERATION:
      debugger;
      return Object.assign({}, state, {
        ...state,
        saved: null,
        loading: false,
      });
    default:
      return state;
  }
};

export default instanceReducer;
