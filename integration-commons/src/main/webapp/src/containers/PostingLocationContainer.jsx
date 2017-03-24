import { connect } from 'react-redux';
import {
  changeStreamType,
} from '../actions/';
import PostingLocation from '../components/PostingLocation/PostingLocation';

const mapStateToProps = state => ({
  streamType: state.instance.streamType,
});

const mapDispatchToProps = dispatch => ({
  switchStreamType: st => dispatch(changeStreamType(st)),
});

const PostingLocationContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(PostingLocation);

export default PostingLocationContainer;
