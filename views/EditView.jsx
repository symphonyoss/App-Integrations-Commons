import React from 'react';
import IntegrationHeader from '../components/IntegrationHeader/IntegrationHeader';
import InputDescription from '../components/InputDescription/InputDescription';
import PostingLocationContainer from '../components/PostingLocation/PostingLocationContainer';
import SubmitInstanceContainer from '../components/SubmitInstance/SubmitInstanceContainer';
import MessageBox from '../components/MessageBox/MessageBox';
import { operations } from '../actions';

const EditView = () => (
  <div>
    <MessageBox />
    <IntegrationHeader showSubTitle={false} />
    <InputDescription />
    <PostingLocationContainer />
    <SubmitInstanceContainer operation={operations.UPDATE} />
  </div>
);

export default EditView;
