import React from 'react';
import { hashHistory } from 'react-router';
import './styles/styles.less';

const onConfigureNew = () => {
  hashHistory.push('/create-view');
};

const ConfigureNew = () => (
  <div className='wrapper configure-new'>
    <header>
      <h2>Configured Integrations</h2>
    </header>
    <button onClick={onConfigureNew} className='button'>Configure New</button>
  </div>
);

export default ConfigureNew;
