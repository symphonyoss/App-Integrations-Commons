import React, { Component, PropTypes } from 'react';
import DataRow from './DataRow';
import '../../styles/main.less';
import './styles/styles.less';

class TableInstance extends Component {
  constructor(props) {
    super(props);
    this.state = {
      instances: [],
    };
    this.onConfigureNew = this.onConfigureNew.bind(this);
  }

  componentWillMount() {
    this.setState({
      instances: this.props.instanceList,
    });
  }

  componentDidMount() {
    this.props.fetchUserId();
    this.props.fetchInstanceList();
  }

  componentWillReceiveProps(nextProps) {
    if (this.props.instanceList !== nextProps.instanceList) {
      this.setState({
        instances: nextProps.instanceList,
      });
    }
  }

  onConfigureNew(e) {
    e.preventDefault();
  }

  render() {
    let dataRowObj = {};
    return (
      <div className='wrapper'>
        <header>
          <h2>Configured Integrations</h2>
          <button onClick={this.onConfigureNew} className="button">Configure New</button>
        </header>
        <table className='table-instance'>
          <thead>
            <tr>
              <th><span>Description</span></th>
              <th><span>Active in</span></th>
              <th><span>Webhook URL</span></th>
              <th><span>Last Posted</span></th>
              <th><span>Actions</span></th>
            </tr>
          </thead>
          <tbody>
            {
              this.state.instances.map((item, index) => {
                dataRowObj = {
                  description: item.description,
                  appName: this.props.appName,
                  streamType: item.streamType,
                  instanceId: item.instanceId,
                  baseWebhookUrl: this.props.baseWebhookUrl,
                  postingLocationRooms: item.postingLocationRooms,
                  lastPosted: item.lastPosted,
                };
                return <DataRow instance={dataRowObj} key={index} id={index} />;
              })
            }
          </tbody>
        </table>
      </div>
    );
  }
}

TableInstance.propTypes = {
  fetchUserId: PropTypes.func.isRequired,
  fetchInstanceList: PropTypes.func.isRequired,
  instanceList: PropTypes.arrayOf(PropTypes.object).isRequired,
  appName: PropTypes.string.isRequired,
  baseWebhookUrl: PropTypes.string.isRequired,
};

export default TableInstance;
