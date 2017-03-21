import { connect } from 'react-redux';
import {
  switchStreamType,
} from '../actions/actions';
import PostingLocation from '../components/PostingLocation/PostingLocation';

const mapStateToProps = state => ({
  streamType: state.streamType,
});

const mapDispatchToProps = dispatch => ({
  switchStreamType: (st) => { dispatch(switchStreamType(st)); },
});

const PostingLocationContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(PostingLocation);

export default PostingLocationContainer;
