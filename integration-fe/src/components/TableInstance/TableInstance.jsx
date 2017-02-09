import React, { Component, PropTypes } from 'react';
import DataRow from './DataRow';
import '../../styles/main.less';
import './styles/styles.less';

class TableInstance extends Component {
  componentWillMount() {
    this.props.fetchUserId();
    this.props.fetchInstanceList();
  }

  render() {
    let dataRowObj = {};
    return (
      <div className='wrapper table-instance'>
        <table className='instances'>
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
              this.props.instanceList.map((item, index) => {
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
