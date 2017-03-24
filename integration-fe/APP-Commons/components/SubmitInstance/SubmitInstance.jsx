import React, { PropTypes, Component } from 'react';
import { hashHistory } from 'react-router';
import {
  operations,
} from '../../actions';
import '../../styles/main.less';

export class SubmitInstance extends Component {
  constructor(props) {
    super(props);
    this.state = {
      label: 'Add',
      requireNameVisible: false,
      requireRoomsVisible: false,
    };
    this.dispatchActions = this.dispatchActions.bind(this);
    this.onCancel = this.onCancel.bind(this);
    this.validateFields = this.validateFields.bind(this);
  }

  componentWillMount() {
    switch (this.props.operation) {
      case operations.UPDATE:
        this.setState({
          label: 'Update',
        });
        break;
      case operations.REMOVE:
        this.setState({
          label: 'Remove',
        });
        break;
      default:
        this.setState({
          label: 'Add',
        });
    }
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.saved !== nextProps.saved) {
      if (nextProps.saved) {
        if (nextProps.operation === 'CREATE' || nextProps.operation === 'UPDATE') {
          hashHistory.push('/instance-created');
        } else if (nextProps.operation === 'REMOVE') {
          hashHistory.push('/');
        }
      } else {
        hashHistory.push('/');
      }
    }
  }

  onCancel() {
    this.props.resetMessage();
    hashHistory.push('/');
  }

  dispatchActions() {
    switch (this.props.operation) {
      case 'CREATE':
        this.props.saveInstance();
        break;
      case 'UPDATE':
        this.props.editInstance();
        break;
      case 'REMOVE':
        this.props.removeInstance();
        break;
      default:
        this.props.saveInstance();
    }
  }

  validateFields() {
    if (this.props.operation !== operations.REMOVE) {
      if (this.props.name === '') {
        this.props.showRequireName();
      } else {
        this.props.hideRequireName();
      }

      if (this.props.streamType === 'CHATROOM') {
        if (this.props.postingRooms.length === 0) {
          this.props.showRequireRooms();
        } else {
          this.props.hideRequireRooms();
        }
      } else {
        this.props.hideRequireRooms();
      }

      if (
        this.props.name === '' ||
        (this.props.streamType === 'CHATROOM' && this.props.postingRooms.length === 0)
      ) {
        return;
      }
    }

    this.dispatchActions();
  }

  render() {
    return (
      <div className='submit-container'>
        <button className='button cancel-link' onClick={this.onCancel}>Cancel</button>
        <button className='button' onClick={this.validateFields}>{this.state.label}</button>
      </div>
    );
  }
}

SubmitInstance.propTypes = {
  saveInstance: PropTypes.func.isRequired,
  editInstance: PropTypes.func.isRequired,
  removeInstance: PropTypes.func.isRequired,
  resetMessage: PropTypes.func.isRequired,
  showRequireName: PropTypes.func.isRequired,
  showRequireRooms: PropTypes.func.isRequired,
  hideRequireName: PropTypes.func.isRequired,
  hideRequireRooms: PropTypes.func.isRequired,
  saved: PropTypes.bool,
  name: PropTypes.string,
  streamType: PropTypes.string,
  postingRooms: PropTypes.arrayOf(PropTypes.string),
  operation: PropTypes.string,
};

SubmitInstance.defaultProps = {
  saved: false,
};

export default SubmitInstance;
