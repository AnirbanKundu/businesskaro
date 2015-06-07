'use strict';

/**
 * @ngdoc overview
 * @name businesskaroApp
 * @description
 * # businesskaroApp
 *
 * Main module of the application.
 */
angular
  .module('businesskaroApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'app/views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'app/views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
