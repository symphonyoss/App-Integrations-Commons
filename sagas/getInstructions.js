import { call, put } from 'redux-saga/effects';
import { getInstructions as getAppInstructions } from './apiCalls';
import {
    FETCH_FAILED,
    GET_APP_INSTRUCTIONS,
} from '../actions';

export function* getInstructions() {
  try {
    const instructions = yield call(getAppInstructions);
    yield put({ type: GET_APP_INSTRUCTIONS, instructions });
  } catch (error) {
    yield put({ type: FETCH_FAILED, error });
  }
}
