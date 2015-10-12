angular.module('theme.core.services')
  .factory("EntityService", ['$http','$q',function($http, $q) {
  'use strict';

  var _getEntityData = function(options){
    var deferred = $q.defer(), data = {},url='';
    if(options.entityType==='USER_PROFILE'){
      url = 'services/userProfile/summary?userId='+options.entityId;
    }
    else if(options.entityType==='OFFER'){
      url = '/services/offer/summary/'+options.entityId;
    }
    else if(options.entityType==='REQUEST'){
      url = '/services/request/summary/'+options.entityId;
    }
    else{
      //NEWS
    }

    $http({
        url: url,
        method: 'GET'
      }).then(function(response){    
        var data = response.data
        deferred.resolve(data);
      },function(error){
        deferred.reject(error);
      });   
    
      return deferred.promise;
    };
    var _getEntityDetailData = function(options){
    var deferred = $q.defer(), data = {},url='';
    if(options.entityType==='USER'){
      url = '/services/userProfile/details/'+options.entityId;
    }
    else if(options.entityType==='OFFER'){
      url = '/services/offer/detail/'+options.entityId;
    }
    else if(options.entityType==='REQUEST'){
      url = '/services/request/detail/'+options.entityId;
    }
    else{
      //NEWS
    }

    $http({
        url: url,
        method: 'GET'
      }).then(function(response){    
        var data = response.data
        deferred.resolve(data);
      },function(error){
        deferred.reject(error);
      });   
    
      return deferred.promise;
    };
    var _getRelatedUserDetail = function(options){
      var deferred = $q.defer();
      $http({
          url: '/services/userProfile/summarybyId/'+options.userId,
          method: 'GET'
        }).then(function(response){
          deferred.resolve(response.data);
        },function(error){
          deferred.reject(error);
        });      
        return deferred.promise;
    };
  return {
    getEntityData: _getEntityData,
    getEntityDetailData:_getEntityDetailData,
    getRelatedUserDetail : _getRelatedUserDetail
  };
}]);