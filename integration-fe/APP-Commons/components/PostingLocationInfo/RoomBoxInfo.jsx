import React, { PropTypes } from 'react';
import '../PostingLocation/RoomBox/styles/styles.less';

const RoomBoxInfo = ({ name, publicRoom, memberCount, creatorPrettyName }) => (
  <div className='room-box'>
    <div>
      <span>{name}</span>
      {!publicRoom && (<span><i className="fa fa-lock" /></span>)}
      <div className='room-info'>
        <span>
          {
            `${memberCount} Member${memberCount > 1 ? 's' : ''},
              created by ${creatorPrettyName}`
          }
        </span>
      </div>
    </div>
  </div>
);

RoomBoxInfo.propTypes = {
  name: PropTypes.string.isRequired,
  publicRoom: PropTypes.bool.isRequired,
  memberCount: PropTypes.number.isRequired,
  creatorPrettyName: PropTypes.string.isRequired,
};

export default RoomBoxInfo;
