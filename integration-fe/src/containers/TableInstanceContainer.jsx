import React from 'react';
import { connect } from 'react-redux';
import {
  fetchInstanceList,
  fetchInstanceListSuccess,
  fetchInstanceListFailure,
  changeIt,
} from '../actions/actions';
import TableInstance from '../components/TableInstance/TableInstance';
// import '../js/Integration';

const mapStateToProps = (state) => {
  debugger;
  return {
    instanceList: state.instances,
    configurationId: state.configurationId,
    text: state.text,
  };
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchInstanceList: () => {
      dispatch(fetchInstanceList()).then((response) => {
        dispatch(fetchInstanceListSuccess(response));
      }, (error) => {
        fetchInstanceListFailure(error);
      });
    },
    changeIt: (val) => {
      debugger;
      dispatch(changeIt(val));
    }
  }
}

const TableInstanceContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(TableInstance);

export default TableInstanceContainer;