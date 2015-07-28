angular
  .module('theme.demos.signup_page', [
    'theme.core.services'
  ])
  .controller('SignupPageController', ['$scope', '$theme', 'UserAuthentication', function($scope, $theme,UserAuthentication) {
    'use strict';
    $theme.set('fullscreen', true);

    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.email ="";
    $scope.password ="";

    $scope.logIn = function(){
      console.log($scope.email);
      console.log($scope.password);
      UserAuthentication.signInUser({userName:$scope.email, password:$scope.password});
    }

  }]);