'use strict';

export const Utils = (function () {

  let pub = {};

  const timestampToDate = (_ts) => {
    const date = new Date(Number(_ts));
    const monthNames = [
      'Jan',
      'Feb',
      'Mar',
      'Apr',
      'May',
      'Jun',
      'Jul',
      'Aug',
      'Sep',
      'Oct',
      'Nov',
      'Dec'
    ];
    const month = monthNames[date.getMonth()];
    return `${month} ${date.getDate()}, ${date.getFullYear()}`;
  }

  pub.getParameterByName = (_name, _url) => {
    let [name, url] = [_name, _url];
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    const regex = new RegExp(`[?&]${name}(=([^&#]*)|&|#|$)`);
    const results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
  }

  pub.normalizeInstanceList = (rawInstanceList) => {
    const instances = [];
    let op; // parsed optional properties
    for (let obj in rawInstanceList) {
      op = JSON.parse(rawInstanceList[obj].optionalProperties);
      instances.push({
        description: rawInstanceList[obj].name,
        instanceId: rawInstanceList[obj].instanceId,
        lastPosted: op.lastPostedDate ? timestampToDate(op.lastPostedDate) : 'not available',
        created: rawInstanceList[obj].createdDate ? timestampToDate(rawInstanceList[obj].createdDate) : 'not available',
        streamType: op.streamType,
        streams: op.streams || [],
        postingLocationRooms: [],
        notPostingLocationRooms: [],
      });
    }
    return instances;
  }

  pub.getUserRooms = (rooms) => {
    const userRooms = [];
    const regExp = /\//g;
    
    for (let obj in rooms) {
      if (rooms[obj].userIsOwner) {
        rooms[obj].threadId = rooms[obj].threadId.replace(regExp, '_').replace("==", "");
        userRooms.push(rooms[obj]);
      }
    }
    return userRooms;
  }
  return pub;
})();

Object.freeze(Utils);
