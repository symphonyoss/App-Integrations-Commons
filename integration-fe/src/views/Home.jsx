import React from 'react';
import TableInstanceContainer from '../containers/TableInstanceContainer';
import SetupInstructions from '../components/SetupInstructions/SetupInstructions';
import IntegrationHeader from '../components/IntegrationHeader/IntegrationHeader';

const Home = () => (
  <div>
    <IntegrationHeader />
    <SetupInstructions />
    <TableInstanceContainer />
  </div>
);

export default Home;
