angular.module('theme.core.services')
  .factory("LookUpService", ['$http','$q',function($http, $q) {
  'use strict';
  var ageGroup = [], educationGroup = [];
  var _getAgeGroup = function(){
    var deferred = $q.defer(); 
    if(ageGroup && ageGroup.length>0){
      deferred.resolve(ageGroup);
    }
    else{
      $http({
        url: 'utilservices/ages',
        method: 'GET'
      }).then(function(response){    
        ageGroup = response.data;  
        deferred.resolve(ageGroup);
      },function(error){
        deferred.reject(error);
      });
    }    
    return deferred.promise;
  };
  var _getEducations = function(){
    var deferred = $q.defer(); 
    if(educationGroup && educationGroup.length>0){
      deferred.resolve(educationGroup);
    }
    else{
      $http({
        url: 'utilservices/education',
        method: 'GET'
      }).then(function(response){    
        educationGroup = response.data;  
        deferred.resolve(educationGroup);
      },function(error){
        deferred.reject(error);
      });
    }    
    return deferred.promise;
  };
  return {
    getAgeGroup: _getAgeGroup,
    getEducations : _getEducations
  };
}]);