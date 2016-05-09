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
       //added by nagendra  
        //controller: 'FeaturedPolicyController',
        //added by nagendra
          
          
      })
      .when('/entitydetail/:type/:entityId',{
        templateUrl: 'views/entitydetail.html',
        resolve: {
          auth: ['$q', 'UserAuthentication', '$location', '$rootScope', function($q, authenticationSvc, $location, $rootScope) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {
              if(!userInfo.profileCreated || userInfo.profileCreated ===0){
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
      
      //Written by nagendra
      .when('/public/featurednews/:entityId',{
        templateUrl: 'views/featurednews.html'      
      })
      .when('/search/:type/:keywords',{
        templateUrl: 'views/search.html',
        resolve: {
            auth : ['$location',function($location){
              console.log('Route matchs');
            }]
        }
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
          auth: ['$q', 'UserAuthentication', '$location', '$rootScope', function($q, authenticationSvc, $location, $rootScope) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {
              if(!$rootScope.profileCreated || $rootScope.profileCreated ===0){
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
      .when('/createrequest/:id?',{
        templateUrl: 'views/createrequest.html',
        resolve: {
          auth: ['$q', 'UserAuthentication', '$location', '$rootScope', function($q, authenticationSvc, $location, $rootScope) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo) {
              if(!$rootScope.profileCreated || $rootScope.profileCreated ===0){
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
      //written by nagendra
       .when('/mypolicies',{
        templateUrl: 'views/mypolicies.html',
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
      .when('/goipolicy/:id?',{
        templateUrl: 'views/form-goi.html',
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
      .when('/userprofile/validate/:registeredToken/email/:guid',{
          templateUrl: 'views/validateregistereduser.html',
          //Written by anirban
          /*resolve: {
              params:['']
          }*/
          //Written by nagendra
         /* resolve: {
            auth: ['$q', 'UserAuthentication', '$location', function($q, authenticationSvc, $location) {
            var userInfo = authenticationSvc.getToken();       
                if (userInfo) {
                    return $q.when(userInfo);
                } else {
                    return $q.reject({ authenticated: false , visitedroute: $location.url() });
                }
            }]
            }
            */

      })
      .when('/login',{
        templateUrl: 'views/login.html',
        resolve: {
          auth: ['$q', 'UserAuthentication', '$location', function($q, authenticationSvc, $location) {
            var userInfo = authenticationSvc.getToken();       
            if (userInfo && userInfo.clientId && userInfo.secureToken){
              $location.path('#/')
            }
          }]
        }
      })
      .when('/signupform',{
          templateUrl: 'views/signupform.html'
      })
      .when('/admin/manageusers', {
           templateUrl: 'views/viewusers.html',
             resolve:{
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
      .when('/admin/manageemails', {
           templateUrl: 'views/manageemails.html',
             resolve:{
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
      .when('/admin/manageentities', {
           templateUrl: 'views/viewentities.html',
             resolve:{
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
      .when('#', {
        templateUrl: 'views/index.html', 
      })
      .when('/changepassword',{
        templateUrl: 'views/changepassword.html',
        resolve:{
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

window.fbAsyncInit = function() {
    FB.init({
      appId      : '1625751014372724',
      xfbml      : true,
      version    : 'v2.5'
    });
  };

  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));