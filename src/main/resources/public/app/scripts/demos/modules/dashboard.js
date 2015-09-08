angular.module('theme.demos.dashboard', [
    'angular-skycons',
    'theme.demos.forms',
    'theme.demos.tasks',
    'theme.core.services'
  ])
  .controller('DashboardController', ['$scope', '$timeout', '$window', '$http' , '$location', '$theme', '$state',function($scope, $timeout, $window,$http, $location,$theme, $state) {
    'use strict';
    var moment = $window.moment;
    var _ = $window._;
    $theme.set('leftbarCollapsed', true);
    //$theme.set('layoutHorizontal', true);
    $scope.$on('$destroy', function() {
      $theme.set('leftbarCollapsed', false);
    });
    $scope.selectedTag = { "selected": [] };
    $scope.selectedValues = undefined;
    $scope.selectedIndustries = { "selected": [] };

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
      var keywords='',
      industry = $scope.selectedIndustries.selected.industryName;
      var state = 'West Bengal';

      if(industry){
        keywords = industry+',';
      }
      keywords += state + ',';
      keywords = keywords.substring(0, keywords.lastIndexOf(','));
      $location.path('/search/ALL/'+keywords);
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
    $scope.industries = [];
    $http.get('utilservices/industries').success(function(response) {
      $scope.industries = response;
    });
    
  }]);