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
    $scope.serverMessage = '';

    var lastRoute = $route.current;
    $scope.$on('$locationChangeSuccess', function() {
      //console.log('$route is', $route);
    });
    $scope.logIn = function(){
      var apphistory = UserAuthentication.getuserRoutes();
      //console.log($scope.loginForm.password);
      //console.log($scope.loginForm.email);
      UserAuthentication.signInUser({email:$scope.loginForm.email, password:$scope.loginForm.password}).then(function(data){
        //$rootScope.profileCreated = data.profileCreated;
        $scope.$emit('loginsuccess', data);
        $scope.serverMessage = '';
        if(apphistory[0]==='/login'){
          //console.log('History is',history);
          $location.path('/'); 
        }
        else{
          $location.path(apphistory[0]);  
        }         
      },function(error){
        $scope.showServerMessage = 'Your username or password was not correct';

      });
    };
    $scope.$on('$routeChangeStart', function() {
      $scope.previouspath = $location.path();
    });

    $scope.$on('$routeChangeSuccess', function() {
      //console.log('routeChangeSuccess success in signuppage.js', $location);
    });



    $scope.canSubmitLoginForm = function() {
      return $scope.form.loginForm.$valid;
    };

  }])
  .controller('NewSignUpController', ['$rootScope', '$scope', '$theme', '$timeout', 'UserAuthentication', '$location','$route', function($rootScope,$scope, $theme, $timeout, UserAuthentication, $location, $route) {
    console.log('In NewSignUpController');
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.email= '';
    $scope.password = '';
    $scope.register = function(){
      var apphistory = UserAuthentication.getuserRoutes(); 
      //console.log($scope.loginForm.password);
      //console.log($scope.loginForm.email);
      UserAuthentication.registerUser({userName:$scope.email, password:$scope.password, email:$scope.email}).then(function(data){
          UserAuthentication.signInUser({userName:$scope.email, password:$scope.password, email:$scope.email}).then(function(data){
              $scope.$emit('loginsuccess', data);
              $location.path('/userprofile/firstLogin'); 
            },function(error){

            });
            //$scope.$emit('loginsuccess', data);
            //$scope.serverMessage = '';

        //$scope.$emit('loginsuccess', data);
        //$scope.serverMessage = '';
        /*if(apphistory[0]==='/signupform'){
          //console.log('History is',history);
          $location.path('/'); 
        }
        else{
          $location.path(apphistory[0]);  
        }  */       
      },function(error){
        $scope.showServerMessage = 'Your username or password was not correct';

      });
    };

  }])
  .controller('RestPasswordController', ['$scope', '$theme', '$http', '$window',function($scope, $theme, $http, $window) {
    console.log('In Password Reset Controller');
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.reset = function(){
    	console.log('In reset'+$scope.resetEmail);
    	$http({
            url: 'services/resetPassword',
            method: 'POST',
            data: {
          	  "email":$scope.resetEmail
            }
          }).then(function(response){
        	  $window.location.href = '/#/login';
          });
    	
    }

  }]);