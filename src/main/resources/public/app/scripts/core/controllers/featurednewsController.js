angular
.module('theme.demos.featurednewscontroller', [])
.controller('FeaturedNewsController', ['$rootScope','$scope', '$http', '$location','$route','LookUpService',function ($rootScope, $scope, $http, $location, $route,LookUpService) 
{
  'use strict';  
    $scope.policyId = $route.current.params.entityId;    
    $scope.waiting = true;
    $scope.relatedTags=[];
  
    $http.get('/services/policy/public/'+$scope.policyId).success(function(response) {
        $scope.policy = response;  
        for(var i=0;i<$scope.policy.industrys.length;i++)
        {
              var industryName = LookUpService.getIndustryName($scope.policy.industrys[i]);
              var t = {
                name: industryName,
                url : '#/search/ALL' + '/' + industryName
              }
              $scope.relatedTags.push(t);
            }
      for(var i=0;i<$scope.policy.states.length;i++)
      {
        var stateName = LookUpService.getStateName($scope.policy.states[i]);
        var t = {
            name: stateName,
            url : '#/search/ALL' + '/' + stateName
        }
        $scope.relatedTags.push(t);
      } 
        $scope.waiting = false;
    }).error(function () {
        $scope.policy = [];
        $scope.waiting = false;
    });       
}]);