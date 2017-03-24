import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import { copyToClipboard } from '../../js/utils.service';
import './styles/styles.less';

class WebHookURLCopy extends Component {
  constructor(props) {
    super(props);
    this.state = {
      enableCopy: true,
    };
    this.webhookurl = `${this.props.baseWebHookURL}/${this.props.instanceId}`;
    this.onCopyURL = this.onCopyURL.bind(this);
  }

  onCopyURL(e) {
    const target = e.target;
    this.setState({
      enableCopy: false,
    });
    e.preventDefault();
    // callback function called from copyToClipboard
    const cb = (value) => {
      if (value) {
        this.setState({
          enableCopy: true,
        });
      }
    };
    copyToClipboard(target, cb);
  }

  render() {
    return (
      <div className='wrapper webhook-url-container'>
        <header>
          <h2>
            <label htmlFor='webhook-url'>Webhook URL</label>
          </h2>
        </header>
        <div className='webhook-content'>
          <input
            id={`webhook-url-${this.props.instanceId}`}
            type="text"
            defaultValue={this.webhookurl}
            disabled='disabled'
            data-value={this.webhookurl}
          />
          {
            this.state.enableCopy ?
              <a
                href='#'
                data-copytarget={`#webhook-url-${this.props.instanceId}`}
                onClick={this.onCopyURL}
              >
                Copy URL
              </a> :
              <a
                href='#'
                data-copytarget={`#webhook-url-${this.props.instanceId}`}
              >
                Copy URL
              </a>
          }
        </div>
      </div>
    );
  }
}

WebHookURLCopy.propTypes = {
  instanceId: PropTypes.string.isRequired,
  baseWebHookURL: PropTypes.string.isRequired,
};

const mapStateToProps = state => ({
  instanceId: state.instance.instanceId,
  baseWebHookURL: state.instance.baseWebHookURL,
});
// const mapStateToProps = (state) => {
//   debugger;
//   return {
//     instanceId: state.instance.instanceId,
//     baseWebHookURL: state.instance.baseWebHookURL,
//   };
// };

export default connect(
  mapStateToProps,
)(WebHookURLCopy);
