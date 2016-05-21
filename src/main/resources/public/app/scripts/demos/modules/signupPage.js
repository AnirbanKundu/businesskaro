angular
  .module('theme.demos.signup_page', [
    'theme.core.services',
    'vcRecaptcha'
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
    $scope.waiting = false;

    var lastRoute = $route.current;
    $scope.$on('$locationChangeSuccess', function() {
      //console.log('$route is', $route);
    });
    //written by nagendra
    $scope.reset=function()
    {
       $scope.loginForm.email= '';
         $scope.loginForm.password = '';
    };
    
    
    $scope.logIn = function(){
      $scope.waiting = true;
      var apphistory = UserAuthentication.getuserRoutes();
      //console.log($scope.loginForm.password);
      //console.log($scope.loginForm.email);
      UserAuthentication.signInUser({email:$scope.loginForm.email, password:$scope.loginForm.password}).then(function(data){
        //$rootScope.profileCreated = data.profileCreated;
        UserAuthentication.getUserDetails().then(function(response){
          $scope.waiting = false;
          $scope.$emit('loginsuccess', response.data);
          $scope.serverMessage = '';
          if(apphistory[0]==='/login'){
            //console.log('History is',history);
            $location.path('/'); 
          }
          else{
            $location.path(apphistory[0]);  
          }
        },function(){
            //Error
        });                 
      },function(error){
        $scope.showServerMessage = 'Your username or password was not correct';
        $scope.waiting = false;
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
  .controller('NewSignUpController', ['$rootScope', '$scope', '$theme', '$timeout', 'UserAuthentication', '$location','$route','vcRecaptchaService', function($rootScope,$scope, $theme, $timeout, UserAuthentication, $location, $route,vcRecaptchaService) {
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.email= '';
    $scope.password = '';
    $scope.repeatpassword = '';
    $scope.isrpwdError = false;
    $scope.serverMessage = '';
    $scope.waiting = false;
    $scope.$watch('repeatpassword',function(newval,oldval){
      if(newval){
        if(newval!==$scope.password){
          $scope.signupform.$setValidity('repeatpassword',false,$scope.signupform);
          $scope.isrpwdError = true;
          
        }else{
          $scope.signupform.$setValidity('repeatpassword',true,$scope.signupform);
          $scope.isrpwdError = false;
          
        }
      }
    });
    //captcha
    $scope.response = null;
    $scope.widgetId = null;

    if($location.host() ==='localhost'){
      $scope.model = {
        key: '6LcoNhwTAAAAAB504XGyp-X1g1EaCpjfiio2qn_H'
      };
    }else{
      $scope.model = {
        key:'6Lf8RxwTAAAAANN5wFDzArq5lZLCs5HVNUFOoTUc'
      };
    }

    $scope.setResponse = function (response) {
        console.info('Response available');

        $scope.response = response;
    };

    $scope.setWidgetId = function (widgetId) {
        console.info('Created widget ID: %s', widgetId);

        $scope.widgetId = widgetId;
    };

    $scope.cbExpiration = function() {
        console.info('Captcha expired. Resetting response object');
        vcRecaptchaService.reload($scope.widgetId);
        $scope.response = null;
     };
    //captcha
    
    
    $scope.register = function($event){
      $event.preventDefault();
      $scope.userRegistered = false;
      $scope.showServerMessage = '';
      if($scope.email && $scope.password && $scope.repeatpassword){
        $scope.waiting = true;
        var apphistory = UserAuthentication.getuserRoutes(); 
        UserAuthentication.registerUser({userName:$scope.email, password:$scope.password, email:$scope.email}).then(function(data){
          $scope.waiting = false;    
          $scope.email = $scope.password = $scope.repeatpassword ="";
          //added by nagendra --CAPTCHA
          vcRecaptchaService.reload($scope.widgetId);
          //added by nagendra --CAPTCHA
          
          $scope.showServerMessage = "A registration email has been sent. Please use the link sent in the email to register in the application."
        },function(error){
          $scope.waiting = false;
          if(error.data.type ==='USER_ALREADY_EXIST'){
            $scope.showServerMessage = 'The email address you have entered is already registered.';
          }else{
            $scope.showServerMessage = 'Unknow error. Please try again.';
          }         

        });
      }
      
      
     

      /**
       * SERVER SIDE VALIDATION
       *
       * You need to implement your server side validation here.
       * Send the reCaptcha response to the server and use some of the server side APIs to validate it
       * See https://developers.google.com/recaptcha/docs/verify
       */
      /*
      var valid;
      console.log('sending the captcha response to the server', $scope.response);

      if (valid) {
          console.log('Success');
      } else {
          console.log('Failed validation');

          // In case of a failed validation you need to reload the captcha
          // because each response can be checked just once
          vcRecaptchaService.reload($scope.widgetId);
      }
      */
    };

  }])
  .controller('RestPasswordController', ['$scope', '$theme', '$http', '$window','$location','$timeout',function($scope, $theme, $http, $window,$location,$timeout) {
    $scope.waiting = false;
    $scope.serverMessage = '';   
    $scope.isPasswordSent=false;
    
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });
    $scope.reset = function(){
      if($scope.isPasswordSent===false){
        $scope.waiting = true; 
            console.log('In reset'+$scope.resetEmail);
            $http({
                  url: 'services/resetPassword',
                  method: 'POST',
                  data: {
                    "email":$scope.resetEmail
                  }
                }).then(function(response){
                  $scope.waiting = false;  
                  $scope.showServerMessage = 'Your temporary password has been emailed. Please use that password to login.';  
                  $scope.isPasswordSent=true;          
                },function(error){          
                  $scope.waiting = false;
                  if(error.data.type ==='USER_AUTH_FAIL'){
                    $scope.showServerMessage = 'User email not found. Please enter a valid email';     
                    $scope.isPasswordSent=false;
                    }else{
                      $scope.showServerMessage = 'Unknow error. Please try again.';
                    }             
                });
      }
      else
      {
        $location.path('/login');
      }
            
    }

  }])
  .controller('ValidateRegisteredUser', ['$scope', '$theme', '$http', '$window', '$timeout', '$route','$location', function($scope, $theme, $http, $window, $timeout,$route, $location) {
    $scope.registeredToken = $route.current.params.registeredToken;
    $scope.guid = $route.current.params.guid;
    $scope.waiting = false;
    $scope.showServerMessage = 'You will soon be redirected ..... Please wait .....';  
    $timeout(function(){
      $scope.waiting = true;  
    },1000) ;
    $theme.set('fullscreen', true);
    $scope.$on('$destroy', function() {
      $theme.set('fullscreen', false);
    });  

    init = function(){      
      $http({
        url: '/services/uservalidate/'+ $scope.registeredToken + '/email/'+ $scope.guid,
          method: 'GET',
        }).then(function(response){
          $scope.waiting = false;  
          var userInfo = {
            secureToken: response.data.secureToken,
            clientId: response.data.clientId
          }; 
          $window.localStorage["bk_userInfo"] = JSON.stringify(userInfo);
          userInfo.profileCreated = response.data.profileCreated;
          $scope.$emit('loginsuccess', userInfo);
          $location.path('/userprofile/firstLogin');  
        },function(error){
            $scope.showServerMessage = 'User has already been registered.';
            $scope.waiting = false;
        });
      }  
    /*
      For Javascript the method needs to be defined and then called. Else will get an JS error
    */
    init();

  }]);