
/* Instance List */
export const FETCH_INSTANCE_LIST = 'FETCH_INSTANCE_LIST';
export const FETCH_INSTANCE_LIST_SUCCESS = 'FETCH_INSTANCE_LIST_SUCCESS';
export const FETCH_INSTANCE_LIST_ERROR = 'FETCH_INSTANCE_LIST_ERROR';

/* Edit instance */
export const EDIT_INSTANCE = 'EDIT_INSTANCE';
export const EDIT_INSTANCE_SUCCESS = 'EDIT_INSTANCE_SUCCESS';
export const EDIT_INSTANCE_ERROR = 'EDIT_INSTANCE_ERROR';

/* Create instance */
export const CREATE_INTANCE = 'CREATE_INTANCE';
export const CREATE_INSTANCE_SUCCESS = 'CREATE_INSTANCE_SUCCESS';
export const CREATE_INSTANCE_ERROR = 'CREATE_INSTANCE_ERROR';


/* 
  Actions for Table Instance component (List View)
*/
export function fetchInstanceList(configurationId) {
  // TODO, make an ajax request to the end poit passing the configurationId parameter...
  return {
    type: FETCH_INSTANCE_LIST,
    payload: response, // ajax request response
  };
}

export function fetchInstanceListSuccess(instanceList) {
  return {
    type: FETCH_INSTANCE_LIST_SUCCESS,
    payload: instanceList,
  };
}

export function fetchInstanceListFailure(error) {
  return {
    type: FETCH_INSTANCE_LIST_ERROR,
    payload: error,
  };
}

/* 
  Actions for Input Description component,
  Posting Location component and
  Suggestions Rooms component (Edit View)
*/
export function editInstanceById(configurationId, instanceId, payload) {
  /*
    TODO, make an ajax request to the end poit passing
    the configurationId, instanceId and instance payload parameters 
  */
  return {
    type: EDIT_INSTANCE,
    response: response, // ajax request response
  }
}

export function editInstanceByIdSuccess(instance) {
  return {
    type: EDIT_INSTANCE_SUCCESS,
    payload: instance,
  };
}

export function editInstanceByIdFailure(error) {
  return {
    type: EDIT_INSTANCE_ERROR,
    error: error,
  };
}

/* 
  Actions for Input Description component,
  Posting Location component and Suggestions Rooms component (Create View)
*/
export function createInstance(configurationId, payload) {
  /*
    TODO, makes an ajax request to the end poit passing the configurationId and the new instance payload.
    This call returns an instance ID, which we can build the new webhook URL, as well
    returns an instanceObj
  */
  return {
    type: CREATE_INTANCE,
    instance: instanceObj,  // ajax request response
  };
}

export function createInstanceSuccess(instance) {
  return {
    type: CREATE_INSTANCE_SUCCESS,
    payload: instance,
  };
}

export function createInstanceFailure(error) {
  return {
    type: CREATE_INSTANCE_ERROR,
    error: error,
  };
}
