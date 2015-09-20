angular
  .module('theme.core.entitydetail_controller', [])
  .controller('EntityDetailController', ['$scope', '$http', '$route', '$state', '$log', '$timeout', 'EntityService','LookUpService', function($scope, $http, $route, $state, $log, $timeout, EntityService,LookUpService) {
    'use strict';

    $scope.entityType = $route.current.params.type.toUpperCase();
    $scope.entityId = $route.current.params.entityId;
    $scope.entityResult = {};
    $scope.userDetail = {};
    LookUpService.getIndustries().then(function(data){
        $scope.industries = data;
    },function(error){
        $log.log(error);
    });
    EntityService.getEntityDetailData({'entityType':$scope.entityType,'entityId':$scope.entityId}).then(function(data){
    	$scope.entityResult = data;
    	console.log('Data in entity detail:',$scope.entityResult);
    	//Get Related Entity details
    	if($scope.entityType!='GOIPOLICY'){
    		if($scope.entityType=='USER'){
    			//GET ALL OFFERS/REQUESTS POSTED BY USER
    		}
    		else{
    			//GET USER Details
    			EntityService.getRelatedUserDetail({'userId':$scope.entityResult.userId}).then(function(data){
    				$scope.userDetail = data;
    			},function(error){
    				console.log('Error now',error);
    			});
    		}
    	}

    },function(error){
    	console.log('Error now');
    });

    $timeout(function(){
      FB.init({
        appId      : '1628152447465336',
        status     : true,
        xfbml      : true,
        version    : 'v2.3' // or v2.0, v2.1, v2.0
      });
    },1000);

  }]);