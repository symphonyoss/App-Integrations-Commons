const instanceReducer = (state = { saved: false }, action) => {
  switch (action.type) {
    case 'GET_ACTIVE_INSTANCE':
      return action.instance;
    case 'CHANGE_INSTANCE_NAME':
      return Object.assign({}, state, {
        ...state,
        name: action.name,
      });
    case 'CHANGE_STREAM_TYPE':
      return Object.assign({}, state, {
        ...state,
        streamType: action.streamType,
      });
    case 'ADD_STREAM_TO_INSTANCE':
      return Object.assign({}, state, {
        ...state,
        streams: state.streams.concat([action.stream]),
      });
    case 'REMOVE_STREAM_FROM_INSTANCE': {
      return Object.assign({}, state, {
        ...state,
        streams: state.streams.filter(item => item !== action.stream),
      });
    }
    case 'SAVE_INSTANCE':
      return Object.assign({}, state, {
        ...state,
        name: '',
        streamType: 'IM',
        streams: [],
        saved: true,
      });
    default:
      return state;
  }
};

export default instanceReducer;
