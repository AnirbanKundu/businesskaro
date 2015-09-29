angular
  .module('themesApp', [
    'theme',
    'theme.demos',
    'StateService'
  ])
  .config(['$provide', '$routeProvider', function($provide, $routeProvider) {
    'use strict';
    $routeProvider
      .when('/', {
        templateUrl: 'views/index.html'
      })
      .when('/entitydetail/:type/:entityId',{
        templateUrl: 'views/entitydetail.html',
        resolve: {
          auth: ['$q', 'UserAuthentication', '$location', '$rootScope', function($q, authenticationSvc, $location, $rootScope) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {
              if($rootScope.profileCreated ===0){
                $location.url('/userprofile/filldetails')
                return false;
              }
              return $q.when(userInfo);
            } else {
              return $q.reject({ authenticated: false , visitedroute: $location.url() });
            }
          }]
        }
      })
      .when('/search/:type/:keywords',{
        templateUrl: 'views/search.html'
      })
      .when('/userprofile/:message?',{
        templateUrl: 'views/profile.html',
        controller: 'RegistrationPageController',
        resolve: {
          auth: ['$q', 'UserAuthentication', '$location', function($q, authenticationSvc, $location) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {

              return $q.when(userInfo);
            } else {
              return $q.reject({ authenticated: false , visitedroute: $location.url() });
            }
          }]
        }
      })
      .when('/createoffer/:id?',{
        templateUrl: 'views/createoffer.html',
        controller: 'CreateOfferController',
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
      .when('/createrequest/:id?',{
        templateUrl: 'views/createrequest.html',
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
      .when('/myoffers',{
        templateUrl: 'views/myoffers.html',
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
      .when('/myrequests',{
        templateUrl: 'views/myrequests.html',
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
      .when('/login',{
        templateUrl: 'views/login.html'
      })
      .when('/signupform',{
        templateUrl: 'views/signupform.html'
      })
      .when('#', {
        templateUrl: 'views/index.html', 
      })
      .when('/changepwd',{
        templateUrl: 'views/changepassword.html',
        auth: ['$q', 'UserAuthentication', '$location', function($q, authenticationSvc, $location) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {
              return $q.when(userInfo);
            } else {
              console.log('current location', $location);
              return $q.reject({ authenticated: false , visitedroute: $location.url() });
            }
        }]
      })
      .when('/resetpassword',{
        templateUrl: 'views/resetpassword.html'
      })
      .when('/contactus',{
        templateUrl: 'views/contactus.html'
      })
      .otherwise({
        redirectTo: '/'
      });
  }])
  .run(['$rootScope', '$location','UserAuthentication', function($rootScope, $location, UserAuthentication){
      $rootScope.$on("$routeChangeError", function(event, current, previous, eventObj) {
        if (eventObj.authenticated === false) {
          $location.path("/login");
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

(function(){
    (function(d, s, id) {
      var js, fjs = d.getElementsByTagName(s)[0];
      if (d.getElementById(id)) return;
      js = d.createElement(s); js.id = id;
      js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.4&appId=1628152447465336";
      fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));

})();