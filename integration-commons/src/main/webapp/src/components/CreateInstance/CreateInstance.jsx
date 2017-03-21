import React, { Component, PropTypes } from 'react';
import InputDescription from '../InputDescription/InputDescription';

import './styles/styles.less';

export default class CreateInstance extends Component {
  constructor(props) {
    super(props);
    this.state = {
      description: '',
      streamType: 'IM',
      error: undefined,
    };

    this.onCreate = this.onCreate.bind(this);
  }

  componentWillMount() {
    if (!this.props.userId) {
      this.props.fetchUserId();
    }
  }

  onCreate() {
    if (!this.state.description) {
      // TODO remove this to reuse the error component that will be created.
      this.setState({ error: 'Description is required' });
      return;
    }

    this.setState({ error: undefined });
    this.props.onCreate(this.state, this.props.userId);
  }

  onCancel() {
    // TODO back to list view
    // hashHistory.push('/list-view');
  }

  render() {
    // TODO remove this to reuse the error component that will be created.
    let error = '';
    if (this.state.error) {
      error = <h1>{this.state.error}</h1>;
    }

    let loading = '';
    if (this.props.loading === true) {
      loading = <h1>CREATING...</h1>;
    } else {
      loading = '';
    }

    return (
      <div className="container-component block">
        {error}
        {loading}
        <InputDescription
          handleChange={description => this.setState({ description })}
        />
        <div className="submit-webhook">
          <div className="btn-container">
            <button className="button" onClick={this.onCreate} type="button" >Add</button>
            <button className="button cancel-link" onClick={this.onCancel}>Cancel</button>
          </div>
        </div>
      </div>
    );
  }
}

CreateInstance.propTypes = {
  onCreate: PropTypes.func.isRequired,
  userId: PropTypes.number,
  loading: PropTypes.bool.isRequired,
  fetchUserId: PropTypes.func.isRequired,
};
