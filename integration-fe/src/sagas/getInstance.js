import { call, put } from 'redux-saga/effects';
import { getInstance as activeInstance } from './apiCalls';

export function* getInstance() {
  try {
    const instance = yield call(activeInstance);
    yield put({ type: 'GET_ACTIVE_INSTANCE', instance });
  } catch (error) {
    yield put({ type: 'FETCH_FAILED', error });
  }
}
