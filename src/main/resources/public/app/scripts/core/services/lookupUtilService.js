angular.module('theme.core.services')
  .factory("LookUpService", ['$http','$q',function($http, $q) {
  'use strict';
  var ageGroup = [], educationGroup = [], professions= [];
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
  var _getProfession = function(){
    var deferred = $q.defer(); 
    if(professions && professions.length>0){
      deferred.resolve(professions);
    }
    else{
      professions = [
        {
          "profession_id" : "1",
          "profession_name" : "Software Engineer"
        },
        {
          "profession_id" : "2",
          "profession_name" : "Architect"
        },
        {
          "profession_id" : "3",
          "profession_name" : "Doctor/Medical"
        },
        {
          "profession_id" : "4",
          "profession_name" : "Lecturer/ Professor"
        }
      ];
      deferred.resolve(professions);
    }    
    return deferred.promise;
  }
  return {
    getAgeGroup: _getAgeGroup,
    getEducations : _getEducations,
    getProfession : _getProfession
  };
}]);