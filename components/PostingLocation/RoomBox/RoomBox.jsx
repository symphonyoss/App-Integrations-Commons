import React, { PropTypes } from 'react';
import './styles/styles.less';

const RoomBox = props => (
  <div className='room-box'>
    <div>
      <span>{props.name}</span>
      {!props.public && (<span><i className="fa fa-lock" /></span>)}
      <div className='room-info'>
        <span>
          {
            `${props.memberCount} Member${props.memberCount > 1 ? 's' : ''},
              created by ${props.creatorPrettyName}`
          }
        </span>
      </div>
    </div>
    <button
      onClick={() => props.removeFilter(props.threadId)}
    >
      <i className="fa fa-times" />
    </button>
  </div>
);

RoomBox.propTypes = {
  name: PropTypes.string.isRequired,
  threadId: PropTypes.string.isRequired,
  public: PropTypes.bool.isRequired,
  memberCount: PropTypes.number.isRequired,
  creatorPrettyName: PropTypes.string.isRequired,
  removeFilter: PropTypes.func.isRequired,
};

export default RoomBox;
