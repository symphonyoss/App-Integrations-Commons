/* eslint-disable no-unused-vars */
import React from 'react';

const config = (function () {
  const pub = {};
  let params = {};
  let Instructions;

  pub.setParams = (values) => {
    params = Object.assign({}, values);
  };

  pub.getParams = () => params;

  pub.setInstructions = (template) => { Instructions = template; };

  pub.getInstructions = () => Instructions;

  return pub;
}());

export default config;
