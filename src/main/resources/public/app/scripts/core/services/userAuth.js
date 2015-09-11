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

  var _registerUser = function(options) {
    var deferred = $q.defer(); 
    $http({
        url: '/services/user',
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
  var _getUserDetailProfile = function(){
    //Get User Details .. ASK DURGA
    var deferred = $q.defer(); 
    $http({
      url: 'services/userProfile',
      method: 'GET'
    }).then(function(response){
      deferred.resolve(response);
    },function(error){
      if(error.status ===400 && error.data.type==="ENTITY_NOT_FOUND"){
        var details = {"ageGroupId": 0, "educatonId":0, "experienceId":0, "faceBookUrl":"","imageUrl":"", "linkedInUrl":"","professionalId":0,"stateId":0,"twiterURL":""};
        var summary = {"aboutMe":"","cityName":"","companyUrl":"","firstName":"","imageUrl":"","industrys":[],"lastName":"","lookinfForSkill":[],"offeredServices":[],"userSkills":[],"userType":""};
        error.data.details = details;
        error.data.summary = summary;
        error.data.newUser = true;
        deferred.resolve(error);
      }
      else{
        deferred.reject(error); 
      }
      
    });

    //var response = {user: {"ImageUrl": ""}};
    //deferred.resolve(response);
    return deferred.promise;
  };

  var _saveUserDetailProfile = function(data){
    var deferred = $q.defer(); 
    $http({
      url: 'services/userProfile',
      method: 'POST',
      data: data
    }).then(function(response){
      deferred.resolve(response);
    },function(error){
      deferred.reject(error);
    });
    return deferred.promise;    
  };
  return {
    signInUser: _signInUser,
    getToken: _getToken,
    getUserDetails : _getUserDetails,
    logOut : _logOut,
    getuserRoutes : _getuserRoutes,
    setuserRoutes : _setuserRoutes,
    getUserDetailProfile : _getUserDetailProfile,
    saveUserDetailProfile : _saveUserDetailProfile,
    registerUser : _registerUser
  };
}]);