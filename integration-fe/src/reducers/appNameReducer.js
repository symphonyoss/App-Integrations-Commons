/* eslint-disable no-debugger */
const appName = (state = '', action) => {
  switch (action.type) {
    case 'SET_APP_NAME':
      return action.payload;
    default:
      return state;
  }
};

export default appName;
