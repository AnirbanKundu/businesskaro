angular
  .module('theme.core.contactus_controller', [])
  .controller('ContactUsController', ['$scope', '$filter', '$http',function($scope, $filter,$http) {
    'use strict';
    $scope.contactusForm={};
    
    $scope.contactus = function($event){
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
            $scope.message = 'success';
            $scope.alert = { type: 'success', msg: 'Email sent succesfully. You would be contacted shortly.'};
            $scope.contactusForm = {};

          },function(error){
            $scope.alert = { type: 'alert', msg: 'Email was not sent. Try again.'};
          });
    }
  }]);