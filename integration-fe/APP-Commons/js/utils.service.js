/* eslint-disable no-useless-escape */
/* eslint-disable no-debugger */
export const Utils = (function utils() {
  const pub = {};
  const userRooms = [];

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
      'Dec',
    ];
    const month = monthNames[date.getMonth()];
    return `${month} ${date.getDate()}, ${date.getFullYear()}`;
  };

  pub.getParameterByName = (_name, _url) => {
    let [name, url] = [_name, _url];
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, '\\$&');
    const regex = new RegExp(`[?&]${name}(=([^&#]*)|&|#|$)`);
    const results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
  };

  pub.normalizeInstanceList = (rawInstanceList) => {
    const instances = [];
    let op; // parsed optional properties
    for (const obj in rawInstanceList) {
      if (obj) {
        op = JSON.parse(rawInstanceList[obj].optionalProperties);
        instances.push({
          instanceId: rawInstanceList[obj].instanceId,
          name: rawInstanceList[obj].name,
          lastPosted: op.lastPostedDate ? timestampToDate(op.lastPostedDate) : 'not available',
          created: rawInstanceList[obj].createdDate ?
            timestampToDate(rawInstanceList[obj].createdDate) : 'not available',
          streamType: op.streamType,
          streams: op.streams || [],
          postingLocationRooms: [],
          notPostingLocationRooms: [],
        });
      }
    }

    // store all posting location rooms into instances...
    instances.map((instance) => {
      if (instance.streamType === 'CHATROOM') {
        instance.streams.map(stream =>
          instance.postingLocationRooms.push(
            userRooms.filter(userRoom => stream === userRoom.threadId)[0])
        );
      }
      return instance;
    });

    // stores all indexes of the rooms (object) that are not posting locations into an array
    let idx,
      aux;

    instances.map((instance) => {
      idx = [];
      aux = userRooms.slice();
      instance.streams.map((stream) => {
        for (let k = 0, n = aux.length; k < n; k += 1) {
          if (aux[k].threadId === stream) {
            idx.push(k);
          }
        }
        return idx;
      });
      // remove from the user rooms array all those are posting locations rooms
      for (let i = 0, n = aux.length; i < n; i += 1) {
        for (let j = 0, l = idx.length; j < l; j += 1) {
          if (i === idx[j]) {
            aux.splice(i, 1);
            idx.splice(j, 1);
            for (let k = 0, s = idx.length; k < s; k += 1) idx[k] -= 1;
            i -= 1;
            break;
          }
        }
      }
      instance.notPostingLocationRooms = aux.slice();
      return instance;
    });
    return instances;
  };

  pub.getUserRooms = (rooms) => {
    const regExp = /\//g;

    for (const obj in rooms) {
      if (rooms[obj].userIsOwner) {
        rooms[obj].threadId = rooms[obj].threadId.replace(regExp, '_').replace('==', '');
        userRooms.push({
          threadId: rooms[obj].threadId,
          name: rooms[obj].name,
          creatorPrettyName: rooms[obj].creatorPrettyName,
          memberCount: rooms[obj].memberCount,
          publicRoom: rooms[obj].publicRoom,
          streamType: rooms[obj].roomType,
        });
      }
    }
    return userRooms;
  };
  return pub;
}());

export function copyToClipboard(target, cb) {
  // Copy to clipboard without displaying an input...
  const textarea = document.createElement('textarea');
  textarea.style.position = 'relative';
  textarea.style.top = 0;
  textarea.style.left = 0;
  textarea.style.width = '1px';
  textarea.style.height = '1px';
  textarea.style.padding = 0;
  textarea.style.border = 0;
  textarea.style.outline = 0;
  textarea.style.boxShadow = 0;
  textarea.style.background = 'transparent';
  textarea.style.fontSize = 0;

  const webhookUrl = document.querySelector(target.dataset.copytarget) ?
    document.querySelector(target.dataset.copytarget).getAttribute('data-value') : null;

  textarea.value = webhookUrl;

  if (textarea) {
    target.parentNode.appendChild(textarea);
    textarea.select();
    try {
      // copy text
      document.execCommand('copy');
      target.innerHTML = 'Copied!';
      setTimeout(() => {
        target.innerHTML = 'Copy URL';
        target.parentNode.removeChild(target.parentNode.getElementsByTagName('textarea')[0]);
        cb(true);
      }, 2000);
    } catch (err) {
      console.error(err);
    }
  } else {
    console.error(`element not found ${textarea}`);
  }
}

Object.freeze(Utils);
