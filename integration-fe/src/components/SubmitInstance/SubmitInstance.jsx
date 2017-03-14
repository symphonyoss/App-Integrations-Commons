import React, { PropTypes, Component } from 'react';
import { hashHistory } from 'react-router';

export class SubmitInstance extends Component {
  componentWillReceiveProps(nextProps) {
    if (this.props.status !== nextProps.status) {
      if (nextProps.status) {
        hashHistory.push('/');
      }
    }
  }

  render() {
    return (
      <div className='submit-container'>
        <button className='button' onClick={() => this.props.saveInstance()}>Add</button>
        <button className='button cancel-link'>Cancel</button>
      </div>
    );
  }
}

SubmitInstance.propTypes = {
  saveInstance: PropTypes.func.isRequired,
  status: PropTypes.bool,
};

export default SubmitInstance;
