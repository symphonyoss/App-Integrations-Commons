import React from 'react';
import MessageBox from '../components/MessageBox/MessageBox';
import IntegrationHeader from '../components/IntegrationHeader/IntegrationHeader';
import TableInstanceContainer from '../components/TableInstance/TableInstanceContainer';
import SetupInstructions from '../components/SetupInstructions/SetupInstructions';
import ConfigureNew from '../components/ConfigureNew/ConfigureNew';

const Home = () => (
  <div>
    <MessageBox />
    <IntegrationHeader showSubTitle={false} />
    <SetupInstructions />
    <ConfigureNew />
    <TableInstanceContainer />
  </div>
);

export default Home;
