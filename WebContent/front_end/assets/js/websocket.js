// 參考: https://blog.csdn.net/benben_2015/article/details/79294547

let socket = {};
socket.username = '/tony';
socket.MyPoint = '/TogetherWS' + socket.username;
socket.path = window.location.pathname;
socket.host = window.location.host;
socket.webCtx = socket.path.substring(0, socket.path.indexOf('/', 1));
socket.endPointURL = 'ws://' + socket.host + socket.webCtx + socket.MyPoint; // webSocket 連線路徑
// webSocket 連線參數名 尚未實體化 WebSocket 物件
let webSocket, resObj;
/* 
 * Title: 組成待發送信息包
 * 
 * socket.data.userName = '9527';
 * socket.data.message = '測試喔';
 * sendMessage(socket.data);
 * 
 * 覆寫功能範例: 
 * webSocket.onmessage = function(event) {
 *   resObj = JSON.parse(event.data);
 *   alert(resObj.userName+" "+resObj.message);
 * }
 */ 
socket.data = {};

connect();

// 連線
function connect() {
  // 建立 webSocket 連線
  webSocket = new WebSocket(socket.endPointURL);

  // 連線中
  webSocket.onopen = function (event) {
    if (webSocket.readyState === 1) {
      // 連線成功即操作...
    }
  };

  // 通信中 接收信息
  webSocket.onmessage = function (event) {
    resObj = JSON.parse(event.data);
  };

  // 通信關閉
  webSocket.onclose = function (event) {
    console.log('webSocket.onclose', event);
  };
}

// 發信息
function sendMessage(socketData) {
  // 連線建立 才可發送 socket 信息
  if (webSocket.readyState === 1) {
    webSocket.send(JSON.stringify(socketData));
  }
}

// 關閉連線
function disconnect() {
  // 通信 關閉websocket連線
  webSocket.close();
}
