import {connect} from 'react-redux';
import {
    CREATE_INTANCE,
    CREATE_INSTANCE_SUCCESS,
    CREATE_INSTANCE_ERROR,
    createStream,
    createInstance,
    createInstanceSuccess
} from '../actions/actions';
import CreateInstance from '../components/CreateInstance/CreateInstance';

const mapStateToProps = (state) => {
    return {
        myValue: state.myValue
    }
}

const mapDispatchToProps = (dispatch) => {
    const configurationId = '578543c2e4b0edcf4f5ff520';
    return {
        onCreate: (instance) => { 
            dispatch(createStream(configurationId, instance)).then((response) => {
                dispatch(createInstance(configurationId, response.payload.id, instance.description)).then((response2) => {
                    dispatch(createInstanceSuccess(response2.payload));
                }, (error) => {
                    fetchInstanceListFailure(error)
                });
            }, (error) => {
                fetchInstanceListFailure(error);
            });
        }
    }
}

const CreateInstanceContainer = connect(
    mapStateToProps,
    mapDispatchToProps
)(CreateInstance);

export default CreateInstanceContainer;