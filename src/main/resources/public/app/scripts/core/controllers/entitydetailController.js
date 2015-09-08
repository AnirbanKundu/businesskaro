angular
  .module('theme.core.entitydetail_controller', [])
  .controller('EntityDetailController', ['$scope', '$http', '$route', '$state', '$log', '$timeout', 'EntityService', function($scope, $http, $route, $state, $log, $timeout, EntityService) {
    'use strict';

    $scope.entityType = $route.current.params.type;



  }]);