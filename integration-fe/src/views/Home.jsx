import React, { Component } from 'react';
import { createStore } from 'redux';
import TableInstanceContainer from '../containers/TableInstanceContainer';
import CreateInstanceContainer from '../containers/CreateInstanceContainer';
import {
  fetchInstanceList,
  fetchInstanceListSuccess,
  fetchInstanceListFailure,
} from '../actions/actions';
import integrationApp from '../reducers/reducers';
import configureStore from '../store/configureStore.js';

const store = configureStore();

console.error('get state: ', store.getState());

let unsubscribe = store.subscribe(() =>
  console.error('get state: ', store.getState())
);


class Home extends Component {
  constructor(props) {
    super(props);
  }

  componentWillMount() {
    
  }

  componentDidMount() {
    // store.dispatch(fetchInstanceList().then((data) => {
    //   console.error('dispatching list: ', data);
    // }, (error) => {
    //   console.error('error dispatching list: ', error);
    // }));
  }

  render() {
    return (
      <div>
        <TableInstanceContainer />
      </div>
    );
  }
};

export default Home;
