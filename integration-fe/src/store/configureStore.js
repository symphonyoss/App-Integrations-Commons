/* eslint-disable global-require */
if (process.env.NODE_ENV === 'production') {
  console.error('prod');
  module.exports = require('./configureStore.prod');
} else {
  console.error('dev');
  module.exports = require('./configureStore.dev');
}

