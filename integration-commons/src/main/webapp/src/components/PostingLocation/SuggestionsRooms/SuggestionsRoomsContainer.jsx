import { connect } from 'react-redux';
import {
  fetchUserRooms,
  fetchUserRoomsSuccess,
  setError,
} from '../../../actions/actions';

import SuggestionsRooms from './SuggestionsRooms';

const mapStateToProps = state => ({
  userRooms: state.userRooms,
  loading: state.loading,
});

const mapDispatchToProps = dispatch => ({
  fetchUserRooms: () => {
    dispatch(fetchUserRooms()).then((rooms) => {
      dispatch(fetchUserRoomsSuccess(rooms));
    }, (error) => {
      setError(error);
    });
  },
});

const SuggestionsRoomsContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(SuggestionsRooms);

export default SuggestionsRoomsContainer;
