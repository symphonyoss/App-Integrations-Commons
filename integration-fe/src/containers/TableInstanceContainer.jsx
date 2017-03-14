import { connect } from 'react-redux';
import TableInstance from '../components/TableInstance/TableInstance';

const mapStateToProps = state => ({
  userId: state.userId,
  appName: state.appName,
  rooms: state.userRooms,
  instanceList: state.instanceList.instances,
  loading: state.instanceList.loading,
  baseWebHookURL: state.baseWebHookURL,
});

const TableInstanceContainer = connect(
  mapStateToProps,
)(TableInstance);

export default TableInstanceContainer;
