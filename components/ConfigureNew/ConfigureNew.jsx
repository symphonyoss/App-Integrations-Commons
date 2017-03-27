import { connect } from 'react-redux';
import React, { PropTypes } from 'react';
import { hashHistory } from 'react-router';
import {
  resetMessage as callResetMessage,
} from '../../actions';
import './styles/styles.less';

const onConfigureNew = (resetMessage) => {
  resetMessage();
  hashHistory.push('/create-view');
};

const ConfigureNew = ({ resetMessage }) => (
  <div className='wrapper configure-new'>
    <header>
      <h2>Configured Integrations</h2>
    </header>
    <button onClick={() => { onConfigureNew(resetMessage); }} className='button'>
      Configure New
    </button>
  </div>
);

ConfigureNew.propTypes = {
  resetMessage: PropTypes.func.isRequired,
};

const mapDispatchToProps = dispatch => ({
  resetMessage: () => { dispatch(callResetMessage()); },
});

export default connect(
  null,
  mapDispatchToProps,
)(ConfigureNew);
