'use strict';

function SocketService() {

  var that = this;
  that.sessionId = '';

  that.connect = function () {
    var socket = new SockJS('/alerts');
    var client = Stomp.over(socket);

    var onConnect = function() {
      console.log("Connected by socket!");
      client.subscribe("/topic/alerts", function(message) {
        console.log("GET ALERT MESSAGE");
        that.messageOut(JSON.parse(message.body));
      });
    };
    client.connect('guest', 'guest', onConnect);
    return client;
  };

  that.disconnect = function (opts, stompClient) {
    if (stompClient !== null && stompClient !== undefined) { stompClient.disconnect(); }
  };

  that.messageOut = function (msg) {
    var r = document.getElementById('response'), p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    let localDate = new Date(msg.date).toLocaleString();
    p.appendChild(document.createTextNode(localDate + " " + msg.message));
    r.appendChild(p);
  };
}

angularApp
.service('SocketService', SocketService);