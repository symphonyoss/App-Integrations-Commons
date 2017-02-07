import React, { Component } from 'react';
import DataRow from './DataRow';

class TableInstance extends Component {
  constructor(props) {
    super(props);
    this.state = {
      instances: []
    }
  }

  componentDidMount() {
    this.props.fetchInstanceList();
    this.setState({
      instances: this.props.instanceList
    })
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.instanceList !== nextProps.instanceList) {
      this.setState({
        instances: nextProps.instanceList
      });
    }
  }

  render() {

    return (
      <div>
        {/*<ul>
          {
            this.state.instances.map((item, index) => (
              <li key={index}>{item.name}</li>
            ))
          }
        </ul>*/}
        <table>
          <thead>
            <tr>
              <th>Description</th>
              <th>Active in</th>
              <th>Webhook URL</th>
              <th>Last Posted</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {
              this.state.instances.map((item, index) => (
                <tr key={index}>
                  <td>{item.description}</td>
                  <td>{item.instanceId}</td>
                  <td>{item.lastPosted}</td>
                </tr>
              ))
            }
          </tbody>
        </table>
      </div>
    );
  }
}

export default TableInstance;