/* eslint-disable no-debugger */
/* eslint-disable no-unused-vars */
import { fork, take, call } from 'redux-saga/effects';
import { getUserId } from './getUserId';
import { getInstanceList } from './getInstanceList';
import { getRooms } from './getRooms';
import { getAppName } from './getAppName';
import { getBaseWebHookURL } from './getBaseWebHookURL';
import { getInstance } from './getInstance';

function* rootSaga() {
  yield [
    fork(getAppName),
    fork(getBaseWebHookURL),
    fork(getInstanceList),
    fork(getUserId),
    fork(getRooms),
    fork(getInstance),
  ];
}

export default rootSaga;
