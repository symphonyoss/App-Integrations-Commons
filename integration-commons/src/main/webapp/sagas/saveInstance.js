/* eslint-disable no-debugger */
import { call, put, select } from 'redux-saga/effects';
import {
  CALL_SAVE_INSTANCE,
  FAILED_OPERATION,
  SUCCESSFULLY_CREATED,
} from '../actions';
import {
  saveInstance as apiSaveInstance,
  addMembership,
  createIM,
  sendWelcomeMessage,
} from './apiCalls';

export function* saveInstance() {
  let imStream,
    success;
  try {
    yield put({ type: CALL_SAVE_INSTANCE });
    debugger;
    const state = yield select();
    const instance = state.instance;
    if (instance.streamType === 'IM') {
      imStream = yield call(createIM);
      instance.streams.push(imStream.id);
    } else if (instance.streamType === 'CHATROOM') {
      if (instance.streams.length > 0) {
        for (const stream in instance.streams) {
          if (instance.streams[stream]) {
            success = yield call(addMembership, instance.streams[stream]);
          }
        }
      }
    }
    instance.creatorId = state.userId;
    const response = yield call(apiSaveInstance, state);
    instance.instanceId = response.instanceId;
    instance.lastPosted = response.lastModifiedDate;
    success = yield put({ type: SUCCESSFULLY_CREATED, instance });
    if (success) {
      yield call(sendWelcomeMessage, instance);
    }
  } catch (error) {
    yield put({ type: FAILED_OPERATION });
  }
}
