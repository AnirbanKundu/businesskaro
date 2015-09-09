angular
  .module('theme.demos.myrequest', [])
  .controller('MyrequestController', ['$scope', '$http', '$window', function($scope,$http,$window){

	  $http.get('/services/request').success(function(response) {
	      $scope.requests = response;
	    });
	  $scope.openRequest = function(offer){
		  $window.location.href = '/#/createrequest?'+offer.id;
	  }
  }]);