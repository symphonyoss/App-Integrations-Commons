import { fork } from 'redux-saga/effects';
import { getUserId } from './getUserId';
import { getRooms } from './getRooms';
import { getParams } from './getParams';
import { getInstructions } from './getInstructions';
import { watchfier } from './watchfier';

function* rootSaga() {
  yield [
    fork(getParams),
    fork(getInstructions),
    fork(getUserId),
    fork(getRooms),
    fork(watchfier),
  ];
}

export default rootSaga;
