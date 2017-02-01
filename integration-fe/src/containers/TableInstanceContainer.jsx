import { connect } from 'react-redux';
import { 
  fetchInstanceList,
  fetchInstanceListSuccess,
  fetchInstanceListFailure, } from '../actions/actions';
import TableInstance from '../components/TableInstance/TableInstance';

const mapStateToProps = (state) => {
  return {
    instanceList: state.instances.instanceList
  };
}
