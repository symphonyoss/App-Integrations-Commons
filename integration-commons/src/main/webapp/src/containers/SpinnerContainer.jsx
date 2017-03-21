import { connect } from 'react-redux';
import Spinner from '../components/Spinner/Spinner';

const mapStateToProps = state => (
  {
    loading: state.loading,
    loadingMessage: state.messages.loadingInstances,
  }
);

const SpinnerContainer = connect(
  mapStateToProps,
)(Spinner);

export default SpinnerContainer;

