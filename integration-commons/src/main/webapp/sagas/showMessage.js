import { call, put, select } from 'redux-saga/effects';
import {
  showMessage as showWarning,
} from './apiCalls';

export function* showMessage() {
  try {
    const state = select(state => state.message);
    const message = yield call(showWarning, state);
    yield put({ type: 'SHOW_MESSAGE', message });
  } catch (error) {
    yield put({ type: 'MESSAGE_ERROR', error });
  }
}
