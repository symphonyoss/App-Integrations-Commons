import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import '../../styles/main.less';
import './styles/styles.less';

class SetupInstructions extends Component {
  constructor(props) {
    super(props);
    this.state = {
      height: 0,
      hidden: true,
    };
    this.content = null;
    this.contentHeight = 0;
    this.updateHeight = this.updateHeight.bind(this);
    this.showHideInstructions = this.showHideInstructions.bind(this);
  }

  componentDidMount() {
    const content = this.content.childNodes[0];
    this.contentHeight = content.offsetHeight;
    if (this.contentHeight <= 70) {
      content.className += ' wrap';
    }
    this.updateHeight();
  }

  updateHeight() {
    this.setState({
      height: this.contentHeight,
    });
  }

  showHideInstructions() {
    if (this.state.hidden) {
      this.content.style.maxHeight = '312.5rem';
    } else {
      this.content.style.maxHeight = '4.375rem';
    }

    this.setState({
      hidden: !this.state.hidden,
    });
  }

  render() {
    return (
      <div className='wrapper setup-instructions' ref={(wrapper) => { this.content = wrapper; }}>
        {
          this.state.height > 70 && (
            <div className='setup-instructions-header'>
              <div>
                <h1>Setup Instructions</h1>
                <p>{`Here are the steps necessary to add the ${this.props.appName} integration.`}</p>
              </div>
              <div>
                <button onClick={this.showHideInstructions} className='show-hide-instructions'>
                  <i
                    className={this.state.hidden ? 'fa fa-chevron-down' : 'fa fa-chevron-up'}
                    aria-hidden='true'
                  />
                </button>
              </div>
            </div>
          )
        }
        <div>
          {this.props.template}
        </div>
      </div>
    );
  }
}

SetupInstructions.propTypes = {
  appName: PropTypes.string,
  template: PropTypes.node,
};

const mapStateToProps = state => ({
  appName: state.appParams.appName,
  template: state.appInstructions,
});

export default connect(
  mapStateToProps,
)(SetupInstructions);
// export default SetupInstructions;
