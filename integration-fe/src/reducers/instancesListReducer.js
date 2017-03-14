/* eslint-disable no-debugger */
const instanceList = (state = { instances: [], loading: true }, action) => {
  switch (action.type) {
    case 'FETCH_INSTANCE_LIST_SUCCESS':
      return Object.assign({}, state, {
        ...state,
        instances: action.payload,
        loading: false,
      });
    default:
      return state;
  }
};

export default instanceList;
