angular
  .module('themesApp', [
    'theme',
    'theme.demos',
  ])
  .config(['$provide', '$routeProvider', function($provide, $routeProvider) {
    'use strict';
    $routeProvider
      .when('/', {
        templateUrl: 'views/index.html'
      })
      .when('/userprofile',{
        templateUrl: 'views/profile.html',
        controller: 'RegistrationPageController',
        resolve: {
          auth: ['$q', 'UserAuthentication', '$location', function($q, authenticationSvc, $location) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {
              return $q.when(userInfo);
            } else {
              console.log('current location', $location);
              return $q.reject({ authenticated: false , visitedroute: $location.url() });
            }
          }]
        }
      })
      .when('/:templateFile', {
        templateUrl: function(param) {
          return 'views/' + param.templateFile + '.html';
        }
      })
      .when('#', {
        templateUrl: 'views/index.html', 
      })
      .otherwise({
        redirectTo: '/'
      });
  }])
  .run(['$rootScope', '$location','UserAuthentication', function($rootScope, $location, UserAuthentication){
      $rootScope.$on("$routeChangeError", function(event, current, previous, eventObj) {
        if (eventObj.authenticated === false) {
          $location.path("/extras-login2");
          var apphistory = UserAuthentication.getuserRoutes();
          if(apphistory && apphistory.length ==0){
            apphistory.push(eventObj.visitedroute);
          }
          else if(apphistory && apphistory.length ==1){
            apphistory.push(eventObj.visitedroute);
          }
          else if(apphistory && apphistory.length>1){
            var previous = apphistory.pop();
            apphistory[0] = previous;
            apphistory[1] = eventObj.visitedroute;
          }
          UserAuthentication.setuserRoutes(apphistory);
          console.log('Rootscope success in app.js', $location);

          if($rootScope.currentroute){
            $rootScope.previous=eventObj.visitedroute;
            $rootScope.current=eventObj.visitedroute;
          }
        }
      });
      $rootScope.$on('$routeChangeSuccess', function() {
        var apphistory = UserAuthentication.getuserRoutes();
        if(apphistory && apphistory.length ==0){
          apphistory.push($location.$$path);
        }
        else if(apphistory && apphistory.length ==1){
          apphistory.push($location.$$path);
        }
        else if(apphistory && apphistory.length>1){
          var previous = apphistory.pop();
          apphistory[0] = previous;
          apphistory[1] = $location.$$path;
        }
        UserAuthentication.setuserRoutes(apphistory);
        console.log('Rootscope success in app.js', $location);

        if($rootScope.currentroute){
          $rootScope.previous=$location.$$path;
          $rootScope.current=$location.$$path;
        }
        
    });
  }]);