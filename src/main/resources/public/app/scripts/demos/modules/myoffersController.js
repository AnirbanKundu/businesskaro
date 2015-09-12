angular
  .module('theme.demos.myoffer', [])
  .controller('MyOfferController', ['$scope', '$http', '$window', '$location', function($scope,$http,$window,$location){

  	  $scope.waiting = true;	
	  $http.get('/services/offer').success(function(response) {
	      $scope.offers = response;
	      $scope.waiting = false;
	    }).error(function(){
	    	$scope.offers = [];
	    	$scope.waiting = false;
	    });
	  
	  $scope.openOffer = function(offer){
		  $window.location.href = '/#/createoffer?id='+offer.id;
	  }

	  $scope.createOffer=function(){
	  	$location.path('/createoffer'); 
	  }
  }]);