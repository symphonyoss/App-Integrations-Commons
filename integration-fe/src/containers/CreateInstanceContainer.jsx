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
        loading: state.loading
    }
}

const mapDispatchToProps = (dispatch) => {
    const configurationId = '578543c2e4b0edcf4f5ff520';
    return {
        onCreate: (instance) => { 
            dispatch(createStream(configurationId, instance)).then((stream) => {
                dispatch(createInstance(configurationId, stream.payload.id, instance.description)).then((instance) => {
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