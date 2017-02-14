import React, { PropTypes } from 'react';

import './styles/styles.less';

const InputDescription = props => (
  <div className='input-description-container'>
    <h5><label htmlFor="ii-name">Description</label></h5>
    <div>
      <input
        type="text"
        className="text-input"
        id="ii-name"
        placeholder="Add a short description here"
        onChange={event => props.handleChange(event.target.value)}
      />
    </div>
  </div>
);

InputDescription.propTypes = {
  handleChange: PropTypes.func.isRequired,
};

export default InputDescription;
