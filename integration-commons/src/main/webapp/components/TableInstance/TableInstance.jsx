/* eslint-disable react/no-unused-prop-types */
import React, { Component, PropTypes } from 'react';
import { hashHistory } from 'react-router';
import DataRow from './DataRow';
import Spinner from '../../components/Spinner/Spinner';
import '../../styles/main.less';
import './styles/styles.less';

class TableInstance extends Component {
  constructor(props) {
    super(props);
    this.onClickEdit = this.onClickEdit.bind(this);
    this.onClickRemove = this.onClickRemove.bind(this);
  }

  componentDidMount() {
    this.props.getInstanceList();
  }

  onClickEdit(_instance) {
    this.props.resetMessage();
    this.props.showEditInstanceView(_instance);
    hashHistory.push('/edit-view');
  }

  onClickRemove(_instance) {
    this.props.showEditInstanceView(_instance);
    hashHistory.push('/remove-view');
  }

  render() {
    return (
      <div>
        <Spinner loading={this.props.loading} />
        <div className='wrapper table-instance'>
          <table className={this.props.loading ? 'instances' : 'instances table-opacity-1'}>
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
              {this.props.instanceList.map((item, index) => {
                const _instance = {
                  name: item.name,
                  appName: this.props.appName,
                  streamType: item.streamType,
                  instanceId: item.instanceId,
                  baseWebHookURL: this.props.baseWebHookURL,
                  postingLocationRooms: item.postingLocationRooms,
                  lastPosted: item.lastPosted,
                };
                return (
                  <DataRow
                    instance={_instance}
                    onClickEdit={this.onClickEdit}
                    onClickRemove={this.onClickRemove}
                    key={index}
                    id={index}
                  />
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
}

TableInstance.propTypes = {
  appName: PropTypes.string.isRequired,
  instanceList: PropTypes.arrayOf(PropTypes.object).isRequired,
  loading: PropTypes.bool.isRequired,
  baseWebHookURL: PropTypes.string,
  getInstanceList: PropTypes.func.isRequired,
  showEditInstanceView: PropTypes.func.isRequired,
  resetMessage: PropTypes.func.isRequired,
};

export default TableInstance;
