angular.module('theme.demos.dashboard', [
    'angular-skycons',
    'theme.demos.forms',
    'theme.demos.tasks'
  ])
  .controller('DashboardController', ['$scope', '$timeout', '$window', '$http' , '$location', function($scope, $timeout, $window,$http, $location) {
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
    $scope.tagTransform = function(newTag){
      var item = {
        tagId: -1,
        name : newTag.toLowerCase()
      };
      return item;
    };

    $scope.searchTags = function(){
      $location.path('/search');
      console.log('Tags selected are:', $scope.selectedTag);
    }

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