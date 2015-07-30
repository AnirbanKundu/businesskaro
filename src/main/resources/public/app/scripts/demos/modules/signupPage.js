angular
  .module('theme.demos.signup_page', [
    'theme.core.services'
  ])
  .controller('SignupPageController', ['$rootScope', '$scope', '$theme', '$timeout', 'UserAuthentication', '$location','$route', function($rootScope,$scope, $theme, $timeout, UserAuthentication, $location, $route) {
    'use strict';
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.email ="";
    $scope.password ="";
    $scope.loginForm = {};
    $scope.form = {};
    $scope.loginForm.email = '';
    $scope.loginForm.password = '';

    var lastRoute = $route.current;
    $scope.$on('$locationChangeSuccess', function() {
      console.log('$route is', $route);
    });
    $scope.logIn = function(){
      var apphistory = UserAuthentication.getuserRoutes();
      console.log($scope.loginForm.password);
      console.log($scope.loginForm.email);
      UserAuthentication.signInUser({userName:$scope.loginForm.email, password:$scope.loginForm.password});
      $timeout(function(){
        if(apphistory[0]==='/extras-login2'){
          console.log('History is',history);
        }
        $location.path(apphistory[0]);        
      },20);
    };
    $scope.$on('$routeChangeStart', function() {
      $scope.previouspath = $location.path();
    });

    $scope.$on('$routeChangeSuccess', function() {
      console.log('routeChangeSuccess success in signuppage.js', $location);
    });



    $scope.canSubmitLoginForm = function() {
      return $scope.form.loginForm.$valid;
    };

  }]);