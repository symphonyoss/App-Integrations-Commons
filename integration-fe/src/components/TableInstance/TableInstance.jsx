import React, { Component } from 'react';
//import DataRow from './DataRow';

class TableInstance extends Component {
  constructor(props) {
    super(props);
    this.state = {
      instances: []
    }
    this.changeText = this.changeText.bind(this);
  }


  componentWillMount() {


    //console.error('state instanceList: ', this.state.instanceList);
    // try {
    //   this.props.fetchInstanceList();
    //   this.container.innerHTML = `success`;
    // } catch (e) {
    //   this.container.innerHTML = `Error: ${e.message}`;

    // }
  }

  componentDidMount() {
    try {
      this.props.fetchInstanceList();
      this.setState({
        instances: this.props.instanceList
      })
      console.error('succerss fetching list: ');
    } catch (e) {
      console.error('error fetching list: ', e);
    }

  }

  componentWillReceiveProps(nextProps) {
    console.error('Receive Props: ', nextProps.instanceList);
    this.setState({
      instances: nextProps.instanceList
    });
  }

  changeText(e) {
    this.props.changeIt(e.target.value);
  }

  render() {

    return (
      <div>
        <input type='text' onChange={this.changeText} />
        <label>Text: {this.props.text}</label>
        <ul>
          {
            this.state.instances.map((item, index) => (
              <li key={index}>{item.name}</li>
            ))
          }
        </ul>

        {/*<table>
          <thead>
            <th>Description</th>
            <th>Active in</th>
            <th>Webhook URL</th>
            <th>Last Posted</th>
            <th>Actions</th>
          </thead>
          <tbody>
            <td>Description content</td>
            <td>Active in content</td>
            <td>Webhook URL content</td>
            <td>Last Posted content</td>
            <td>Actions content</td>
          </tbody>
        </table>*/}
      </div>
    );
  }
}

export default TableInstance;