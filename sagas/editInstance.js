import { call, put, select } from 'redux-saga/effects';
import {
  setInstance,
  addMembership,
  editInstance as callEditInstance,
} from './apiCalls';

export function* editInstance() {
  try {
    const state = yield select();
    yield call(setInstance, state.instance);
    if (state.instance.streamType === 'CHATROOM') {
      if (state.instance.streams.length > 0) {
        for (const stream in state.instance.streams) {
          if (state.instance.streams[stream]) {
            try {
              yield call(addMembership, state.instance.streams[stream]);
            } catch (error) {
              yield put({ type: 'ADD_MEMBER_SHIP_FAILED', error });
            }
          }
        }
      }
    }
    yield call(callEditInstance, state);
    yield put({ type: 'SUCCESSFULLY_UPDATED' });
  } catch (error) {
    yield put({ type: 'FETCH_FAILED', error });
    yield put({ type: 'FAILED_OPERATION' });
  }
}
