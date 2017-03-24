import { connect } from 'react-redux';
import {
  saveInstance,
} from '../actions';
import SubmitInstance from '../components/SubmitInstance/SubmitInstance';

let store = {};

const mapStateToProps = (state) => {
  store = Object.assign({}, state.instance);
  return {
    status: state.instance.saved,
  };
};

const mapDispatchToProps = dispatch => ({
  saveInstance: () => { dispatch(saveInstance(store)); },
});

const SubmitInstanceContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(SubmitInstance);

export default SubmitInstanceContainer;
