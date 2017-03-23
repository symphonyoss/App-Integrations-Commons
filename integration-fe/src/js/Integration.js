/*
  Global singleton object
*/


const INTEGRATION = (function() {
  
  let pub = {}; // Public methods

  let configurationId;

  pub.setConfigurationId = (val) => {
    configurationId = val;
  }

  pub.getConfigurationId = () => configurationId;

  return pub;

})();

Object.freeze(INTEGRATION);

if (typeof window !== 'undefined') {
  if (window.INTEGRATION === undefined) {
    window.INTEGRATION = INTEGRATION;
  }
}

if (typeof module !== 'undefined') {
  if (global.INTEGRATION === undefined) {
    global.INTEGRATION = INTEGRATION;
  }
}