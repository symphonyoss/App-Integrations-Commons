/* eslint-disable no-debugger */
import { call, put } from 'redux-saga/effects';
import { getUserId as getUser } from './apiCalls';

export function* getUserId() {
  try {
    const userId = yield call(getUser);
    yield put({ type: 'FETCH_USER_ID_SUCCESS', payload: userId });
  } catch (error) {
    debugger;
    yield put({ type: 'FETCH_FAILED', error });
  }
}
