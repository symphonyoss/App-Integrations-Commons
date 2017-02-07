import React from 'react';
import { connect } from 'react-redux';
import {
  fetchInstanceList,
  fetchInstanceListSuccess,
  fetchInstanceListError,
  fetchUserRooms,
  fetchUserRoomsSuccess,
  fetchUserRoomsError,
  changeIt,
} from '../actions/actions';
import TableInstance from '../components/TableInstance/TableInstance';

const mapStateToProps = (state) => {
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
          fetchInstanceListError(error);
        })
        
      }, (error) => {
        fetchUserRoomsError(error);
      })
    }
  }
}

const TableInstanceContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(TableInstance);

export default TableInstanceContainer;