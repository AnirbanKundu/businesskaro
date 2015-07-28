angular.module('theme.core.services')
  .factory('UserAuthentication', ['$http', function ($http) {
    'use strict';
    return {
      signInUser: function(options){
        return $http({
          url: 'services/login',
          method: 'POST',
          data: options,
        }).then(function(response){
          console.log('User Token & client id is :', response);
          return response.data;
        });
      }
    }; 
  }]);