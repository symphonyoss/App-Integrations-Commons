import React, { Component } from 'react';

let integrationConfService;

class TableInstance extends Component {
  
  componentDidMount() {
    integrationConfService = SYMPHONY.services.subscribe("integration-config");

    const promisedList = integrationConfService.getConfigurationInstanceList(configurationId);
  }

  render() {
    return(
      <div>This is a table</div>
    );
  }
}

export default TableInstance;