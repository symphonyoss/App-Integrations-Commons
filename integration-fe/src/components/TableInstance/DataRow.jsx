import React, { Component, PropTypes } from 'react';
import './styles/styles.less';
import { copyToClipboard } from '../../js/utils.service';

class DataRow extends Component {
  constructor(props) {
    super(props);

    this.state = {
      enableCopy: true,
    };
    this.webhookurl = `${this.props.instance.baseWebhookUrl}/${this.props.instance.instanceId}`;
    this.onCopyURL = this.onCopyURL.bind(this);
    this.onClickEdit = this.onClickEdit.bind(this);
    this.onClickRemove = this.onClickRemove.bind(this);
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

  onClickEdit(e) {
    e.preventDefault();
  }

  onClickRemove(e) {
    e.preventDefault();
  }

  render() {
    return (
      <tr>
        <td>
          <span>{this.props.instance.name}</span>
        </td>
        <td>
          {
            this.props.instance.streamType === 'IM' ?
              <span>One on one chat with {this.props.instance.appName}</span> :
                <ul className='active-in'>
                  {
                    this.props.instance.postingLocationRooms.map((room, idx) =>
                      <li key={idx}>
                        <span>{room.name}</span>
                      </li>
                    )
                  }
                </ul>
          }
        </td>
        <td>
          <div className='webhookurl-container'>
            <span
              className='webhookurl'
              id={`webhook-url-${this.props.id}`}
              data-value={this.webhookurl}
            >
              {this.webhookurl}
            </span>
            {
              this.state.enableCopy ?
                <a
                  href='#'
                  data-copytarget={`#webhook-url-${this.props.id}`}
                  onClick={this.onCopyURL}
                >
                  Copy URL
                </a> :
                  <a
                    href='#'
                    data-copytarget={`#webhook-url-${this.props.id}`}
                  >
                    Copy URL
                  </a>
            }
          </div>
        </td>
        <td>
          <span>{this.props.instance.lastPosted}</span>
        </td>
        <td>
          <ul>
            <li><a href='#' onClick={this.onClickEdit} >Edit</a></li>
            <li><a href='#' onClick={this.onClickRemove} >Remove</a></li>
          </ul>
        </td>
      </tr>
    );
  }
}

DataRow.propTypes = {
  instance: PropTypes.shape({
    name: PropTypes.string.isRequired,
    appName: PropTypes.string.isRequired,
    streamType: PropTypes.string.isRequired,
    instanceId: PropTypes.string.isRequired,
    baseWebhookUrl: PropTypes.string.isRequired,
    postingLocationRooms: PropTypes.arrayOf(PropTypes.object),
    lastPosted: PropTypes.string.isRequired,
  }),
  id: PropTypes.number.isRequired,
};

export default DataRow;
