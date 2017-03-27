import React from 'react';
import IntegrationHeader from '../components/IntegrationHeader/IntegrationHeader';
import InputDescriptionInfo from '../components/InputDescriptionInfo/InputDescriptionInfo';
import PostingLocationInfo from '../components/PostingLocationInfo/PostingLocationInfo';
import WebHookURLCopy from '../components/WebHookURLCopy/WebHookURLCopy';
import SubmitConfirmation from '../components/SubmitConfirmation/SubmitConfirmation';

const InstanceCreated = () => (
  <div>
    <IntegrationHeader showSubTitle={false} />
    <InputDescriptionInfo />
    <PostingLocationInfo />
    <WebHookURLCopy />
    <SubmitConfirmation />
  </div>
);

export default InstanceCreated;
