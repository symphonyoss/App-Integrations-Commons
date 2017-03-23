import {connect} from 'react-redux';
import {
    CREATE_INTANCE,
    CREATE_INSTANCE_SUCCESS,
    ERROR,
    createStream,
    createInstance,
    createInstanceSuccess,
    setError,
} from '../actions/actions';
import CreateInstance from '../components/CreateInstance/CreateInstance';

const mapStateToProps = (state) => {
    return {
        loading: state.loading,
        userId: state.userId
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onCreate: (instance, userId) => { 
            dispatch(createStream(instance)).then((stream) => {
                dispatch(createInstance(stream.payload.id, instance.description, userId)).then((instance) => {
                    dispatch(createInstanceSuccess(instance.payload));
                }, (error) => {
                    setError(error)
                });
            }, (error) => {
                setError(error);
            });
        }
    }
}

const CreateInstanceContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(CreateInstance);

export default CreateInstanceContainer;