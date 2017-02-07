import React from 'react';
import { connect } from 'react-redux';
import {
  fetchUserId,
  fetchUserIdSuccess,
  fetchInstanceList,
  fetchInstanceListSuccess,
  fetchUserRooms,
  fetchUserRoomsSuccess,
  setError,
} from '../actions/actions';
import TableInstance from '../components/TableInstance/TableInstance';

const mapStateToProps = (state) => {
  debugger;
  return {
    instanceList: state.instances,
    configurationId: state.configurationId,
  };
}

const mapDispatchToProps = (dispatch) => {
  return {
    fetchInstanceList: () => {
      dispatch(fetchUserRooms()).then((rooms) => {
        dispatch(fetchUserRoomsSuccess(rooms));
        dispatch(fetchInstanceList()).then((list) => {
          dispatch(fetchInstanceListSuccess(list));

        }, (error) => {
          setError(error);
        })

      }, (error) => {
        setError(error);
      })
    },
    fetchUserId: () => {
      dispatch(fetchUserId()).then((user) => {
        dispatch(fetchUserIdSuccess(user));
      }, (error) => {
        setError(error);
      })
    }
  }
}

const TableInstanceContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(TableInstance);

export default TableInstanceContainer;