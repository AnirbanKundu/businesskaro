angular
  .module('theme.demos.myrequest', [])
  .controller('MyrequestController', ['$scope', '$http', function($scope,$http){

	  $http.get('/services/request').success(function(response) {
	      $scope.requests = response;
	    });
	  
  }]);