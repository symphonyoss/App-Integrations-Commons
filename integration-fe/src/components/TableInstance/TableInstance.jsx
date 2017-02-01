import React, { Component } from 'react';
import INTEGRATION from '../../js/Integration';

class TableInstance extends Component {

  componentDidMount() {
    let integrationConfService;

    // try {
    //   const integrationConfService = SYMPHONY.services.subscribe("integration-config");
    //   integrationConfService.getConfigurationInstanceList(configurationId).then((data) => {
    //     console.log('redux: ', data);
    //   });
    // } catch (e) {
    //   console.log('redux error: ', e.message);
    // }

  }

  render() {
    return (
      <div>This is a table</div>
    );
  }
}

export default TableInstance;