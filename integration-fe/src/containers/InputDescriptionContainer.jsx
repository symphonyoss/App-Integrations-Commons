import { connect } from 'react-redux';
import InputDescription from '../components/InputDescription/InputDescription';
import {
  changeInstanceName,
} from '../actions';

const mapStateToProps = state => ({
  name: state.instance.name,
});

const mapDispatchToProps = dispatch => ({
  handleChange: value => dispatch(changeInstanceName(value)),
});

const InputDescriptionContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(InputDescription);

export default InputDescriptionContainer;
