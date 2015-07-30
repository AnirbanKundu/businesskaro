angular.module('theme.core.services')
  .factory("UserAuthentication", ['$http','$q','$window',function($http, $q, $window) {
  'use strict';
  var userInfo, userRoutes = []; 
  var _signInUser = function(options) {
    var deferred = $q.defer(); 
    $http({
        url: 'services/login',
        method: 'POST',
        data: options,
      }).then(function(response) {
      userInfo = {
        secureToken: response.data.secureToken,
        clientId: response.data.clientId
      };
      $window.localStorage["bk_userInfo"] = JSON.stringify(userInfo);
        deferred.resolve(userInfo);
    }, function(error) {
      deferred.reject(error);
    }); 
    return deferred.promise;
  };

  var _getToken = function(){
    if($window && $window.localStorage && $window.localStorage["bk_userInfo"]){
      return JSON.parse($window.localStorage["bk_userInfo"]);
    }
  } ;

  var _getUserDetails = function(){
    var deferred = $q.defer(); 
    $http({
      url: 'services/user',
      method: 'GET'
    }).then(function(response){
      deferred.resolve(response);
    },function(error){
      deferred.reject(error);
    });
    return deferred.promise;
  };

  var _logOut = function(){
    return $http({
      url: '/',
      method: 'GET'
    }).then(function(response){
      if($window && $window.localStorage){
        if($window.localStorage['bk_userInfo']){
          $window.localStorage.removeItem('bk_userInfo');
        }
      }
    },function(error){
      
    });
  };
  var _getuserRoutes = function(){
    if(userRoutes && userRoutes.length ===0){
      return userRoutes;
    }
    /*
    else if(userRoutes && userRoutes.length ===1){
      return userRoutes[0];
    }
    */
    else{
      return userRoutes;
    }
  };
  var _setuserRoutes = function(paths){
    /*
    if(userRoutes && userRoutes.length ===0){
      userRoutes.push(path);
    }
    else {
      var previous = userRoutes.shift();
      userRoutes[0] = previous;
      userRoutes[1] = path;
    }
    */
    userRoutes = paths;
  };
  return {
    signInUser: _signInUser,
    getToken: _getToken,
    getUserDetails : _getUserDetails,
    logOut : _logOut,
    getuserRoutes : _getuserRoutes,
    setuserRoutes : _setuserRoutes
  };
}]);