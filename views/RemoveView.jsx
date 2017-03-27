import React from 'react';
import IntegrationHeader from '../components/IntegrationHeader/IntegrationHeader';
import InputDescriptionInfo from '../components/InputDescriptionInfo/InputDescriptionInfo';
import PostingLocationInfo from '../components/PostingLocationInfo/PostingLocationInfo';
import WebHookURLCopy from '../components/WebHookURLCopy/WebHookURLCopy';
import SubmitInstanceContainer from '../components/SubmitInstance/SubmitInstanceContainer';
import { operations } from '../actions';

const RemoveView = () => (
  <div>
    <IntegrationHeader showSubTitle={false} />
    <InputDescriptionInfo />
    <PostingLocationInfo />
    <WebHookURLCopy />
    <SubmitInstanceContainer operation={operations.REMOVE} />
  </div>
);

export default RemoveView;
