import React, { Component, PropTypes } from 'react';
import './styles/styles.less';

export class Spinner extends Component {
  constructor(props) {
    super(props);
    this.state = {
      style: 'spinner',
    };
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.loading !== nextProps.loading) {
      if (!nextProps.loading) {
        this.setState({
          style: 'spinner spinner-opacity-0',
        });
        setTimeout(() => {
          this.setState({
            style: 'spinner spinner-opacity-0 spinner-height-0',
          });
        }, 700);
      }
    }
  }

  render() {
    return (
      <div className={this.state.style}>
        <i className="fa fa-circle-o-notch fa-spin" />
        <p>Loading...</p>
      </div>
    );
  }
}

Spinner.propTypes = {
  loading: PropTypes.bool.isRequired,
};

export default Spinner;
