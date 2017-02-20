/* eslint-disable no-debugger */
/* eslint-disable arrow-body-style */
import { connect } from 'react-redux';
import {
  fetchUserRooms,
  fetchUserRoomsSuccess,
  setError,
} from '../../../actions/actions';

import SuggestionsRooms from './SuggestionsRooms';

const mapStateToProps = (state) => {
  // debugger;
  return {
    userRooms: state.userRooms,
    loading: state.loading,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    fetchUserRooms: () => {
      dispatch(fetchUserRooms()).then((rooms) => {
        dispatch(fetchUserRoomsSuccess(rooms));
      }, (error) => {
        setError(error);
      });
    },
  };
};

const SuggestionsRoomsContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(SuggestionsRooms);

export default SuggestionsRoomsContainer;
