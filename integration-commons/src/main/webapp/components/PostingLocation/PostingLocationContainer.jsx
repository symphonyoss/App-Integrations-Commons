import { connect } from 'react-redux';
import {
  changeStreamType,
} from '../../actions/';
import PostingLocation from './PostingLocation';

const mapStateToProps = state => ({
  streamType: state.instance.streamType,
  instance: state.instance,
});

const mapDispatchToProps = dispatch => ({
  switchStreamType: st => dispatch(changeStreamType(st)),
});

const PostingLocationContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(PostingLocation);

export default PostingLocationContainer;
