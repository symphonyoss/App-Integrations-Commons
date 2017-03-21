import React, { Component } from 'react';
import config from '../../js/config.service';
import './styles/styles.less';
import Content from './template';

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
                <p>{`Here are the steps necessary to add the ${config.app_name} integration.`}</p>
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
        <Content />
      </div>
    );
  }
}

export default SetupInstructions;
