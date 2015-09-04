angular
  .module('theme.demos.myoffer', [])
  .controller('MyOfferController', ['$scope', function($scope){
	  console.log('In MyOfferController');
	  $scope.name="Ritesh";
  }]);