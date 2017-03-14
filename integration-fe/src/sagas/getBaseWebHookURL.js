/* eslint-disable no-debugger */
import { call, put } from 'redux-saga/effects';
import { getBaseWebHookURL as getURL } from './apiCalls';

export function* getBaseWebHookURL() {
  try {
    const whurl = yield call(getURL);
    yield put({ type: 'SET_BASE_WEBHOOK_URL', payload: whurl });
  } catch (error) {
    debugger;
    yield put({ type: 'FETCH_FAILED', error });
  }
}
