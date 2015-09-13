angular
  .module('theme.demos.myrequest', [])
  .controller('MyrequestController', ['$scope', '$http', '$window', '$location', function($scope,$http,$window,$location){
  		$scope.waiting = true;	
	  $http.get('/services/request').success(function(response) {
	      $scope.requests = response;
	      $scope.waiting = false;
	    }).error(function(){
	    	$scope.offers = [];
	    	$scope.waiting = false;
	    });
	  $scope.openRequest = function(offer){
		  $window.location.href = '/#/createrequest?id='+offer.id;
	  }
	  $scope.createRequest=function(){
	  	$location.path('/createrequest'); 
	  }
  }]);