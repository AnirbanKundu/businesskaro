//Policy Management Controller

angular
.module('theme.demos.mypolicy', ['ui.bootstrap'])
.controller('MyPolicyController', ['$rootScope','$scope', '$http', '$location', function ($rootScope, $scope, $http, $location) {

    $scope.waiting = true;
    $http.get('/services/getAllPolicies').success(function(response) {
        $scope.policies = response;         
        $scope.waiting = false;
    }).error(function () {
        $scope.policies = [];
        $scope.waiting = false;
    });    
   $scope.editPolicy=function(policy){
     $location.url('/goipolicy/'+policy.policyId);
   }
   $scope.createPolicy=function(){
      $location.url('/goipolicy'); 
    }
}]);