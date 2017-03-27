import { call, put, select } from 'redux-saga/effects';
import {
  removeInstance as deactive,
} from './apiCalls';

export function* removeInstance() {
  try {
    const instance = yield select(state => state.instance);
    yield call(deactive, instance);
    yield put({ type: 'SUCCESSFULLY_REMOVED' });
  } catch (error) {
    yield put({ type: 'FETCH_FAILED', error });
    yield put({ type: 'FAILED_OPERATION' });
  }
}
