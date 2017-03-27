const config = (function () {
  const pub = {};
  let params = {};
  let instructions;

  pub.setParams = (values) => {
    params = Object.assign({}, values);
  };

  pub.getParams = () => params;

  pub.setInstructions = (template) => {
    instructions = template;
  };

  pub.getInstructions = () => instructions;

  return pub;
}());

export default config;
