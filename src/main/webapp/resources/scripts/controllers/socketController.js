'use strict';

angularApp
.controller('socketController', function ($scope, SocketService) {

  $scope.sendEndpoint = '';
  $scope.stompClient = null;

  $scope.connectAll = function (context) {
    $scope.sendEndpoint = context;
    $scope.stompClient = SocketService.connect();
  };

  $scope.disconnect = function () { SocketService.disconnect($scope.stompClient); };

});