angular
  .module('theme.demos.registration_page', [])
  .controller('RegistrationPageController', ['$scope', '$timeout' , '$log', '$http', 'LookUpService', function($scope, $timeout, $log, $http,LookUpService) {
    'use strict';
    $scope.checking = false;
    $scope.checked = false;
    $scope.selectedAgeId=0;
    $scope.selectedEducationId=0;
    /*********** Get all Lookup values *********/
    LookUpService.getAgeGroup().then(function(data){
      $scope.ageGroup = data;
    },function(error){
      $log.log(error); 
    });
    LookUpService.getEducations().then(function(data){
      $scope.educations = data;
    },function(error){
      $log.log(error);
    });
    $scope.selectedCountry = {};
    $scope.selectedIndustries = {};
    $scope.industries = [];
    $http.get('utilservices/industries').success(function(response) {
      $scope.industries = response;
    });
    /*********** End of all loop ups ***********/
    $scope.addNewDocument = function() {
      var documentInput = angular.element("#documentInput");
      var file = documentInput[0].files[0];
        var formData = new FormData();
        formData.append('file', file);
        $http({
          url: '/services/upload/',
          method: 'POST',
          headers: { 'Content-Type' : undefined},
          transformRequest: function(data) { return data; },
          data: formData,
          cache : false
        }).then(function(response){
            return response.data;
          }, function(response){
              alert("Error loading file... Please try again.");
            return response.data;
          });
    };
    $scope.checkAvailability = function() {
      if ($scope.reg_form.username.$dirty === false) {
        return;
      }
      $scope.checking = true;
      $timeout(function() {
        $scope.checking = false;
        $scope.checked = true;
      }, 500);
    };
  }]);