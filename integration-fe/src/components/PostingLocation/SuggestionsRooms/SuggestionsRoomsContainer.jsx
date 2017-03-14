import { connect } from 'react-redux';
import {
  addStreamToInstance,
  removeStreamFromInstance,
} from '../../../actions/';

import SuggestionsRooms from './SuggestionsRooms';

const mapStateToProps = state => ({
  userRooms: state.userRooms,
});

const mapDispatchToProps = dispatch => ({
  addStreamToInstance: (stream) => { dispatch(addStreamToInstance(stream)); },
  removeStreamFromInstance: (stream) => { dispatch(removeStreamFromInstance(stream)); },
});

const SuggestionsRoomsContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(SuggestionsRooms);

export default SuggestionsRoomsContainer;
