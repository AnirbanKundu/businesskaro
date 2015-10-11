angular
  .module('theme.demos.myoffer', [])
  .controller('MyOfferController', ['$scope', '$http','$location', function($scope,$http,$location){

  	  $scope.waiting = true;	
	  $http.get('/services/offer').success(function(response){
	      $scope.offers = response;
	      $scope.waiting = false;
	    }).error(function(){
	    	$scope.offers = [];
	    	$scope.waiting = false;
	    });
	  
	  $scope.openOffer = function(offer){
		 $location.url('/createoffer?id='+offer.id);
	  }

	  $scope.createOffer=function(){
	  	$location.url('/createoffer'); 
	  }
  }]);