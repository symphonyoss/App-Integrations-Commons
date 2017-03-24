/* eslint-disable no-debugger */
import { call, put } from 'redux-saga/effects';
import { getRooms as getUserRooms } from './apiCalls';

export function* getRooms() {
  try {
    const rooms = yield call(getUserRooms);
    yield put({ type: 'FETCH_ROOMS_SUCCESS', payload: rooms });
  } catch (error) {
    debugger;
    yield put({ type: 'FETCH_FAILED', error });
  }
}
