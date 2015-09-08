angular
  .module('theme.demos.myoffer', [])
  .controller('MyOfferController', ['$scope', '$http', function($scope,$http){

	  $http.get('/services/offer').success(function(response) {
	      $scope.offers = response;
	    });
	  
  }]);