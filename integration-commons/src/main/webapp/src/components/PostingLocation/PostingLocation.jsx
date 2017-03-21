import React, { PropTypes, Component } from 'react';
import SuggestionsRoomsContainer from './SuggestionsRooms/SuggestionsRoomsContainer';
import './styles/styles.less';

// Use named export for unconnected component (for tests)
export class PostingLocation extends Component {
  constructor(props) {
    super(props);

    this.state = {
      suggestions: false,
    };
    this.onChange = this.onChange.bind(this);
  }

  onChange(e) {
    this.setState({
      suggestions: e.target.id !== 'IM',
    });
    this.props.switchStreamType(e.target.id);
  }

  render() {
    return (
      <div className='wrapper posting-location'>
        <header>
          <h2>Active In</h2>
        </header>
        <div className='radio-group'>
          <div className='radio'>
            <input
              type="radio"
              id='IM'
              name='posting'
              onChange={this.onChange}
              checked={this.props.streamType === 'IM'}
            />
            <label htmlFor="IM">New one-on-one chat</label>
          </div>
          <div className='radio'>
            <input
              type="radio"
              id='CHATROOM'
              name='posting'
              onChange={this.onChange}
              checked={this.props.streamType === 'CHATROOM'}
            />
            <div className='chat-room-info'>
              <label htmlFor="CHATROOM">Existing chat room</label>
              <p>
                You can only add this integration to a room of which you are an owner.
                You can choose one or more rooms.
              </p>
            </div>
          </div>
        </div>
        { this.state.suggestions && (<SuggestionsRoomsContainer />) }
      </div>
    );
  }
}

PostingLocation.propTypes = {
  streamType: PropTypes.string.isRequired,
  switchStreamType: PropTypes.func.isRequired,
};

export default PostingLocation;
