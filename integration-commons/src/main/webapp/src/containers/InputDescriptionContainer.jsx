import { connect } from 'react-redux';
import {
  changeDescription,
} from '../actions/actions';
import InputDescription from '../components/InputDescription/InputDescription';

const mapStateToProps = state => ({
  description: state.description,
});

const mapDispatchToProps = dispatch => ({
  handleChange: (value) => { dispatch(changeDescription(value)); },
});

const InputDescriptionContainer = connect(
  mapStateToProps,
  mapDispatchToProps,
)(InputDescription);

export default InputDescriptionContainer;
