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

        if($rootScope.current){
          $rootScope.previous=$location.$$path;
          $rootScope.current=$location.$$path;
        }
        
    });
  }]);