import { connect } from 'react-redux';
import {
  createStream,
  createInstance,
  createInstanceSuccess,
  fetchUserId,
  fetchUserIdSuccess,
  setError,
} from '../actions/actions';
import CreateInstance from '../components/CreateInstance/CreateInstance';

const mapStateToProps = state => ({
  loading: state.loading,
  userId: state.userId,
});

const mapDispatchToProps = dispatch => ({
  onCreate: (instance, userId) => {
    dispatch(createStream(instance)).then((stream) => {
      dispatch(createInstance(stream.payload.id, instance.description, userId)).then((instance) => {
        dispatch(createInstanceSuccess(instance.payload));
      }, (error) => {
        setError(error);
      });
    }, (error) => {
      setError(error);
    });
  },
  fetchUserId: () => {
    dispatch(fetchUserId()).then((user) => {
      dispatch(fetchUserIdSuccess(user));
    }, (error) => {
      setError(error);
    });
  },
});

const CreateInstanceContainer = connect(
  mapStateToProps,
  mapDispatchToProps
)(CreateInstance);

export default CreateInstanceContainer;
