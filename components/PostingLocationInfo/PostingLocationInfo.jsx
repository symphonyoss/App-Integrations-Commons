/* eslint-disable react/no-unused-prop-types */
import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import RoomBoxInfo from './RoomBoxInfo';
import './styles/styles.less';

const PostingLocationInfo = ({ postingLocations, streamType }) => (
  <div>
    {
      streamType === 'CHATROOM' && (
        <div className='wrapper posting-location-info'>
          <header>
            <h2>Active In</h2>
          </header>
          <ul>
            {
              postingLocations.map((room, idx) => (
                <li key={idx}>
                  <RoomBoxInfo
                    name={room.name}
                    publicRoom={room.publicRoom}
                    memberCount={room.memberCount}
                    creatorPrettyName={room.creatorPrettyName}
                  />
                </li>
              ))
            }
          </ul>
        </div>
      )
    }
  </div>
);

PostingLocationInfo.propTypes = {
  postingLocations: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.string.isRequired,
    publicRoom: PropTypes.bool.isRequired,
    memberCount: PropTypes.number.isRequired,
    creatorPrettyName: PropTypes.string.isRequired,
  })),
  streamType: PropTypes.string.isRequired,
};

const mapStateToProps = state => ({
  streamType: state.instance.streamType,
  postingLocations: state.instance.postingLocationRooms.slice(),
});

export default connect(
  mapStateToProps,
)(PostingLocationInfo);
