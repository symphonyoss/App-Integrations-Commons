import React, { PropTypes, Component } from 'react';
import { connect } from 'react-redux';
import { hashHistory } from 'react-router';
import {
  submitDone,
} from '../../actions';
import '../../styles/main.less';

class SubmitConfirmation extends Component {
  componentWillReceiveProps(nextProps) {
    if (this.props.loading !== nextProps.loading) {
      if (nextProps.loading) {
        hashHistory.push('/');
      }
    }
  }

  render() {
    return (
      <div className='wrapper'>
        <div className='submit-container'>
          <button className='button' onClick={() => { this.props.callSubmitDone(); }}>Done</button>
        </div>
      </div>
    );
  }
}

SubmitConfirmation.propTypes = {
  callSubmitDone: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
};

const mapStateToProps = state => ({
  loading: state.instanceList.loading,
});

const mapDispatchToProps = dispatch => ({
  callSubmitDone: () => { dispatch(submitDone()); },
});

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(SubmitConfirmation);
