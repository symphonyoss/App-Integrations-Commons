// import $ from 'jquery';
import { Utils } from '../js/utils.service';


const configurationId = Utils.getParameterByName('configurationId');
// const botUserId = Utils.getParameterByName('botUserId');
const appName = Utils.getParameterByName('id');
const baseUrl = `${window.location.protocol}//${window.location.hostname}/integration`;
const baseWebHookURL = `${baseUrl}/v1/whi/${appName}/${configurationId}`;

export const getAppName = () => appName;

export const getBaseWebHookURL = () => baseWebHookURL;

export const getInstance = () => ({
  instanceId: null,
  name: '',
  configurationId,
  creatorId: null,
  streamType: 'IM',
  lastPostedDate: null,
  streams: [],
});

export const getUserId = () => {
  const extendedUserService = SYMPHONY.services.subscribe('extended-user-service');
  return extendedUserService.getUserId().then(data => data);
};

export const getRooms = () => {
  const extendedUserService = SYMPHONY.services.subscribe('extended-user-service');
  return extendedUserService.getRooms().then(data => Utils.getUserRooms(data));
};

export const getList = () => {
  const integrationConfService = SYMPHONY.services.subscribe('integration-config');
  return integrationConfService.getConfigurationInstanceList(configurationId)
  .then(data => Utils.normalizeInstanceList(data));


  // return $.ajax({
  //   type: "GET",
  //   beforeSend: (request) => {
  //     request.setRequestHeader("sessionToken", "e7ebb1a25537e40fb5d49bafd6db995d24b9deed5
  // 0b851c7b40546d120c60faf87deabc5c0415b84e82612bbe99a14928a58e75b68d6db18365d6140b8854799");
  //     request.setRequestHeader("Symphony-Anonymous-Id", "d84bfa9f-a7bd-4433-b0af-716333da5f24");
  //     request.setRequestHeader("X-Symphony-CSRF-Token", "e6e61b9a24b81363f609d2ffabd3613129bea
  // 4f3c40f727663b3c945b6dcd4de94c392ac0114f5918b8417df58bd492ba4b93a9d733e53d9a4972363616bac29");
  //   },
  //   url: 'https://nexus.symphony.com/pod/v1/admin/configuration/57bf581ae4b079de6a1cbbf9/instanc
  // e/list',
  //   success: (data) => {
  //     debugger;
  //     console.error("success: ", data);
  //   },
  //   error: (err) => {
  //     debugger;
  //     console.error("error: ", err);
  //   },
  // });
};
