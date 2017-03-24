import { call, put } from 'redux-saga/effects';
import { getParams as getAppParams } from './apiCalls';
import {
    FETCH_FAILED,
    GET_APP_PARAMS,
} from '../actions';

export function* getParams() {
  try {
    const params = yield call(getAppParams);
    yield put({ type: GET_APP_PARAMS, params });
  } catch (error) {
    yield put({ type: FETCH_FAILED, error });
  }
}
