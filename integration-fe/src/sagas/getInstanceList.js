/* eslint-disable no-debugger */
import { call, put } from 'redux-saga/effects';
import { getList } from './apiCalls';

export function* getInstanceList() {
  try {
    const instances = yield call(getList);
    yield put({ type: 'FETCH_INSTANCE_LIST_SUCCESS', payload: instances });
  } catch (error) {
    debugger;
    console.error(error.message);
    yield put({ type: 'FETCH_FAILED', error });
  }
}
