import { takeEvery, put } from 'redux-saga/effects';
import { getInstanceList } from './getInstanceList';
import { getInstance } from './getInstance';
import { saveInstance } from './saveInstance';
import { editInstance } from './editInstance';
import { removeInstance } from './removeInstance';
import {
  EDIT_INSTANCE,
  FETCH_FAILED,
  FETCH_INSTANCE_LIST,
  REMOVE_INSTANCE,
  SAVE_INSTANCE,
} from '../actions';

export function* watchfier() {
  try {
    yield [
      takeEvery(FETCH_INSTANCE_LIST, getInstanceList),
      takeEvery(FETCH_INSTANCE_LIST, getInstance),
      takeEvery(SAVE_INSTANCE, saveInstance),
      takeEvery(EDIT_INSTANCE, editInstance),
      takeEvery(REMOVE_INSTANCE, removeInstance),
    ];
  } catch (error) {
    yield put({ type: FETCH_FAILED, error });
  }
}
