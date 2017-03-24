/* eslint-disable no-debugger */
import { call, put } from 'redux-saga/effects';
import { getAppName as appName } from './apiCalls';

export function* getAppName() {
  try {
    const name = yield call(appName);
    yield put({ type: 'SET_APP_NAME', payload: name });
  } catch (error) {
    debugger;
    yield put({ type: 'FETCH_FAILED', error });
  }
}
