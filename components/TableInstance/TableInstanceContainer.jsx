import { connect } from 'react-redux';
import {
  getInstanceList,
  getInstanceInfo,
  resetMessage as callResetMessage,
} from '../../actions';

import TableInstance from './TableInstance';

const mapStateToProps = state => ({
  userId: state.userId,
  appName: state.appParams.appName,
  rooms: state.userRooms,
  instanceList: state.instanceList.instances,
  loading: state.instanceList.loading,
  baseWebHookURL: state.instance.baseWebHookURL,
});

const mapDispatchToProps = dispatch => ({
  getInstanceList: () => { dispatch(getInstanceList()); },
  showEditInstanceView: (_instance) => { dispatch(getInstanceInfo(_instance)); },
  resetMessage: () => { dispatch(callResetMessage()); },
});

const TableInstanceContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(TableInstance);

export default TableInstanceContainer;
