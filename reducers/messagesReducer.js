import {
  FAILED_OPERATION,
  FAILED_OPERATION_MESSAGE,
  GET_APP_PARAMS,
  HIDE_REQUIRE_NAME,
  HIDE_REQUIRE_ROOMS,
  NAME_IS_REQUIRED,
  RESET_MESSAGE,
  REQUIRED_NAME,
  REQUIRED_ROOMS,
  ROOMS_IS_REQUIRED,
  SUCCESSFULLY_CREATED,
  SUCCESSFULLY_CREATED_MESSAGE,
  SUCCESSFULLY_REMOVED,
  SUCCESSFULLY_REMOVED_MESSAGE,
  SUCCESSFULLY_UPDATED,
  SUCCESSFULLY_UPDATED_MESSAGE,
} from '../actions';

const messages = (state = { appName: '', type: '', text: [] }, action) => {
  switch (action.type) {
    case GET_APP_PARAMS:
      return Object.assign({}, state, {
        ...state,
        appName: action.params.appName,
      });
    case SUCCESSFULLY_CREATED:
      return Object.assign({}, state, {
        type: 'success',
        text: [SUCCESSFULLY_CREATED_MESSAGE.replace(/#INTEGRATION_NAME/g, state.appName)],
      });
    case SUCCESSFULLY_UPDATED:
      return Object.assign({}, state, {
        type: 'success',
        text: [SUCCESSFULLY_UPDATED_MESSAGE.replace(/#INTEGRATION_NAME/g, state.appName)],
      });
    case SUCCESSFULLY_REMOVED:
      return Object.assign({}, state, {
        type: 'success',
        text: [SUCCESSFULLY_REMOVED_MESSAGE.replace(/#INTEGRATION_NAME/g, state.appName)],
      });
    case REQUIRED_ROOMS:
      return Object.assign({}, state, {
        type: 'warning',
        text: (() => {
          if (state.text.filter(msg => msg === ROOMS_IS_REQUIRED).length > 0) {
            return state.text.slice();
          }
          return state.text.concat([ROOMS_IS_REQUIRED]);
        })(),
      });
    case REQUIRED_NAME:
      return Object.assign({}, state, {
        type: 'warning',
        text: (() => {
          if (state.text.filter(msg => msg === NAME_IS_REQUIRED).length > 0) {
            return state.text.slice();
          }
          return state.text.concat([NAME_IS_REQUIRED]);
        })(),
      });
    case HIDE_REQUIRE_NAME:
      return Object.assign({}, state, {
        type: (() => {
          let _type = '';
          if (state.text.filter(msg => msg !== NAME_IS_REQUIRED).length > 0) {
            _type = 'warning';
          }
          return _type;
        })(),
        text: (() => {
          let _text = [];
          if (state.text.filter(msg => msg !== NAME_IS_REQUIRED).length > 0) {
            _text = state.text.filter(msg => msg !== NAME_IS_REQUIRED);
          }
          return _text;
        })(),
      });
    case HIDE_REQUIRE_ROOMS:
      return Object.assign({}, state, {
        type: (() => {
          let _type = '';
          if (state.text.filter(msg => msg !== ROOMS_IS_REQUIRED).length > 0) {
            _type = 'warning';
          }
          return _type;
        })(),
        text: (() => {
          let _text = [];
          if (state.text.filter(msg => msg !== ROOMS_IS_REQUIRED).length > 0) {
            _text = state.text.filter(msg => msg !== ROOMS_IS_REQUIRED);
          }
          return _text;
        })(),
      });
    case RESET_MESSAGE:
      return Object.assign({}, state, {
        type: '',
        text: [],
      });
    case FAILED_OPERATION:
      return Object.assign({}, state, {
        type: 'error',
        text: [FAILED_OPERATION_MESSAGE],
      });
    default:
      return state;
  }
};

export default messages;
