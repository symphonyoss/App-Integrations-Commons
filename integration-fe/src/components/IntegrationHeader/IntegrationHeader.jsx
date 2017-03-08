import React from 'react';
import config from '../../js/config.service';
import './styles/styles.less';

const IntegrationHeader = () => (
  <div className='wrapper integration-header'>
    <header>
      <figure>
        <img src={`../../img/${config.logo}`} alt={config.app_name} />
      </figure>
      <h2>{config.app_name}</h2>
    </header>
  </div>
);

export default IntegrationHeader;
