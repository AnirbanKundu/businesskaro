angular
  .module('theme.demos.myoffer', [])
  .controller('MyOfferController', ['$scope', '$http','$location','$timeout', function($scope,$http,$location, $timeout){

  	  $scope.waiting = true;	
	  $http.get('/services/offer').success(function(response){
	      $scope.offers = response;
	      $scope.waiting = false;
	    }).error(function(){
	    	$scope.offers = [];
	    	$scope.waiting = false;
	    });
	  
	  $scope.openOffer = function(offer){
	  	$timeout(function(){
	  		$location.url('/createoffer/'+offer.id);
	  	},50);		
	  }

	  $scope.createOffer=function(){
	  	$location.url('/createoffer'); 
	  }
  }]);