/* eslint-disable no-unused-vars  */
import React from 'react';
import IntegrationHeader from '../components/IntegrationHeader/IntegrationHeader';
import InputDescriptionContainer from '../containers/InputDescriptionContainer';
import PostingLocationContainer from '../containers/PostingLocationContainer';

const CreateView = () => {
  const onCreate = () => {
  };

  return (
    <div>
      <IntegrationHeader />
      <InputDescriptionContainer />
      <PostingLocationContainer />
    </div>
  );
};

export default CreateView;

