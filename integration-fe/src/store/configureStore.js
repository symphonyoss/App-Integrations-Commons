// if (process.env.NODE_ENV === 'production' || (location && location.hostname !== 'localhost')) {
//   module.exports = require('./configureStore.prod');
// } else {
//   module.exports = require('./configureStore.dev');
// }
import { createStore, applyMiddleware, compose } from 'redux';
import promise from 'redux-promise';
import reducer from '../reducers/reducers';


export default function configureStore(initialState) {
  const finalCreateStore = compose(
    applyMiddleware(promise),
    window.devToolsExtension ? window.devToolsExtension() : f => f
  )(createStore);

  const store = finalCreateStore(reducer, initialState);

  if (module.hot) {
    // Enable Webpack hot module replacement for reducers
    module.hot.accept('../reducers', () => {
      const nextReducer = require('../reducers');
      store.replaceReducer(nextReducer);
    });
  }

  return store;
}