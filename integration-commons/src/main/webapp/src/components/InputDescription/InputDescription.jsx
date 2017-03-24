import React, { PropTypes } from 'react';
import './styles/styles.less';

const InputDescription = props => (
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
      placeholder="Add a short description here"
      onChange={e => props.handleChange(e.target.value)}
      defaultValue={props.name}
      autoFocus
    />
  </div>
);

InputDescription.propTypes = {
  name: PropTypes.string.isRequired,
  handleChange: PropTypes.func.isRequired,
};

export default InputDescription;
