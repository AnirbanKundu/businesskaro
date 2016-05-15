angular
  .module('theme.core.contactus_controller', [])
  .controller('ContactUsController', ['$scope', '$filter', '$http',function($scope, $filter,$http) {
    'use strict';
    $scope.contactusForm={};
    $scope.phoneNumbr = /^\+?\d{2}[- ]?\d{5}[- ]?\d{5}$/;

    $scope.resetValidationForm = function() {      
      $scope.contactusForm.contacterName = '';
      $scope.contactusForm.email = '';
      $scope.contactusForm.mobile = '';
      $scope.contactusForm.subject = '';
      $scope.contactusForm.message = '';
      };
    
    
    $scope.contactus = function($event){
      $scope.waiting = true;
      $event.preventDefault();
      $http({
            url: 'services/contactus',
            method: 'POST',
            data: {
              "name":$scope.contactusForm.contacterName,
              "email":$scope.contactusForm.email,
              "mobileNo":$scope.contactusForm.mobile,
              "subject":$scope.contactusForm.subject,
              "message":$scope.contactusForm.message
            }
          }).then(function(response){
          $scope.waiting = false;
            $scope.message = 'success';
            $scope.alert = { type: 'success', msg: 'Email sent succesfully. You would be contacted shortly.'};
            $scope.contactusForm = {};

          },function(error){
            $scope.alert = { type: 'alert', msg: 'Email was not sent. Try again.'};
          });
    }
  }]);