/* eslint-disable no-unused-vars */
import React, { PropTypes } from 'react';
import { connect } from 'react-redux';
import '../InputDescription/styles/styles.less';

const InputDescriptionInfo = ({ name }) => (
  <div className='wrapper input-description'>
    <header>
      <h2>
        <label htmlFor='input-description'>Description</label>
      </h2>
    </header>
    <input
      type="text"
      className="text-input"
      id="input-description"
      defaultValue={name}
      disabled='disabled'
    />
  </div>
);

InputDescriptionInfo.propTypes = {
  name: PropTypes.string.isRequired,
};

const mapStateToProps = state => ({
  name: state.instance.name,
});

export default connect(
  mapStateToProps,
)(InputDescriptionInfo);
