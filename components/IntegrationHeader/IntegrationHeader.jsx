import { connect } from 'react-redux';
import React, { PropTypes } from 'react';
// import {
//   INTEGRATION_LOGO,
//   INTEGRATION_SUBTITLE,
// } from '../../actions';
import '../../styles/main.less';
import './styles/styles.less';

const IntegrationHeader = ({ showSubTitle, logo, subtitle }) => (
  <div className='wrapper integration-header'>
    <header>
      <figure>
        <img src={`../../img/${logo}`} alt={subtitle} />
      </figure>
      <h2>{subtitle}</h2>
    </header>
    {
      showSubTitle && (
        <div className='sub-title'>
          {subtitle}
        </div>
      )
    }
  </div>
);

IntegrationHeader.propTypes = {
  showSubTitle: PropTypes.bool,
  logo: PropTypes.string,
  subtitle: PropTypes.string,
};

IntegrationHeader.defaultProps = {
  showSubTitle: false,
};

const mapStateToProps = state => ({
  logo: state.appParams.appLogo,
  subtitle: state.appParams.appSubTitle,
});

export default connect(
  mapStateToProps,
)(IntegrationHeader);
