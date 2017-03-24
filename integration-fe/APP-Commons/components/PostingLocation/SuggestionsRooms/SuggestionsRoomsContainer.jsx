/* eslint-disable no-debugger */
import { connect } from 'react-redux';
import {
  addStreamToInstance,
  removeStreamFromInstance,
  resetPostingLocationRooms,
} from '../../../actions/';

import SuggestionsRooms from './SuggestionsRooms';

const mapStateToProps = state => ({
  userRooms: state.userRooms,
  filters: state.instance.postingLocationRooms,
});

const mapDispatchToProps = dispatch => ({
  addStreamToInstance: (room) => { dispatch(addStreamToInstance(room)); },
  removeStreamFromInstance: (room) => { dispatch(removeStreamFromInstance(room)); },
  resetPostingLocation: () => { dispatch(resetPostingLocationRooms()); },
});

const SuggestionsRoomsContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(SuggestionsRooms);

export default SuggestionsRoomsContainer;
