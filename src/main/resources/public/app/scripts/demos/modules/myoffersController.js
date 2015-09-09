angular
  .module('theme.demos.myoffer', [])
  .controller('MyOfferController', ['$scope', '$http', '$window', function($scope,$http,$window){

	  $http.get('/services/offer').success(function(response) {
	      $scope.offers = response;
	    });
	  
	  $scope.openOffer = function(offer){
		  $window.location.href = '/#/createoffer?id='+offer.id;
	  }
  }]);