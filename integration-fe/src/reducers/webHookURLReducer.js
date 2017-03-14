/* eslint-disable no-debugger */
const baseWebHookURL = (state = '', action) => {
  switch (action.type) {
    case 'SET_BASE_WEBHOOK_URL':
      return action.payload;
    default:
      return state;
  }
};

export default baseWebHookURL;
