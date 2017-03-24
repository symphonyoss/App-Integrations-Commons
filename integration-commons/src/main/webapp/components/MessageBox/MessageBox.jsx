import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import './styles/styles.less';

class MessageBox extends Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: this.props.type !== '',
      containerStyle: 'message-box-container wrapper',
      boxStyle: 'show-box',
    };
    this.closeBox = this.closeBox.bind(this);
  }

  componentWillReceiveProps(nextProps) {
    if (this.props !== nextProps) {
      if (nextProps.type !== '' && nextProps.type !== 'success') {
        this.setState({
          visible: true,
        });
        const boxes = this.container.children;
        this.props.text.map((msg) => {
          for (let i = 0, l = boxes.length; i < l; i += 1) {
            if (boxes[i].getElementsByTagName('p')[0].innerHTML === msg) {
              boxes[i].className = `message-box ${this.props.type} show-box`;
            }
          }
        });
      }
    }
  }

  componentWillUnmount() {
    this.setState({
      containerStyle: 'message-box-container wrapper closed',
    });
    const boxes = this.container.children;
    this.props.text.map(() => {
      for (let i = 0, l = boxes.length; i < l; i += 1) {
        boxes[i].className = `message-box ${this.props.type} hide-box`;
      }
    });
  }

  closeBox(idx) {
    this[idx].className = `message-box ${this.props.type} hide-box`;
  }

  render() {
    return (
      <div
        className={this.state.containerStyle}
        ref={(container) => { this.container = container; }}
      >
        {
          this.state.visible && (
          this.props.text.map((msg, idx) =>
            <div
              className={`message-box ${this.props.type} show-box`}
              ref={(elem) => { this[idx] = elem; }}
              key={idx}
            >
              <p>{msg}</p>
              <button onClick={() => { this.closeBox(idx); }}>
                <i className='fa fa-times' />
              </button>
            </div>
        ))
        }
      </div>
    );
  }
}

MessageBox.propTypes = {
  type: PropTypes.string.isRequired,
  text: PropTypes.arrayOf(PropTypes.string).isRequired,
};

const mapStateToProps = state => ({
  type: state.message.type,
  text: state.message.text,
});

export default connect(
  mapStateToProps,
)(MessageBox);
