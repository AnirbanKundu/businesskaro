angular.module('theme.demos.dashboard', [
    'angular-skycons',
    'theme.demos.forms',
    'theme.demos.tasks'
  ])
  .controller('DashboardController', ['$scope', '$timeout', '$window', '$http', function($scope, $timeout, $window,$http) {
    'use strict';
    var moment = $window.moment;
    var _ = $window._;
    $scope.selectedTag = { "selected": [] };
    $scope.selectedValues = undefined;

    $scope.tags = [];
    /*
    $scope.getTags = function(val) {
      if(val){
        $http.get('services/tag/names?keyword').success(function(data) {
          $scope.tags = data;
        });
      }      
    };
    */

    $scope.refreshTags = function(value) {
     if(value=="" || value==undefined ){
    	 return;
     }	
      var params = {keyword: value};
      return $http.get(
        'services/tag/names?',
        {params: params}
      ).then(function(response) {
        $scope.tags = response.data;
      });
    };
    /*
    $http.get('services/tag/names?keyword').success(function(response) {
      $scope.tags = response;
    });
    */
    
  }]);