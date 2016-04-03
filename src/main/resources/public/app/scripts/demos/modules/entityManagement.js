angular
  .module('theme.demos.adminmanage', ['ui.bootstrap','theme.core.search_controller'])
  .controller('EntityManagementController', ['$rootScope','$scope', '$http', '$location','$modal','$log', 'LookUpService',function ($rootScope, $scope, $http, $location,$modal,$log, LookUpService) {

    $scope.waiting = false;
    $scope.SELECT = 'Please Select';
    $scope.selectedEntity = "SELECT";
    $scope.selectedIndustry =  "SELECT";
    $scope.selectedState = "SELECT"

    LookUpService.getIndustries().then(function(data){
        $scope.industries = data;
    },function(error){
        $log.log(error);
    });

    LookUpService.getStates().then(function(data){
        $scope.states = data;
      },function(error){
        $log.log(error);
    });

    $scope.searchEntity = function(){
      $scope.waiting = true;
      if($scope.selectedEntity===$scope.SELECT || $scope.selectedIndustry===$scope.SELECT|| $scope.selectedState===$scope.SELECT){
        $scope.waiting = false;
        return false;
      }
      
      $http({
          url: '/admin/manageEntity?entityType='+ $scope.selectedEntity +'&industryId='+$scope.selectedIndustry+'&stateId='+$scope.selectedState,
          method: 'GET',
          cache : false
        }).then(function(response){  
          $scope.waiting = false;         
          $scope.message = 'success';
          $scope.entities = response.data;
        },function(error){
          $scope.waiting = false;
          //$scope.alert = { type: 'alert', msg: '<strong>User role</strong> was not updated. Try again.'};
      });
    }
      
    $scope.editEntity=function(e){
      var url = '';
      if($scope.selectedEntity==='OFFER'){
        url = '/createoffer/'+e.entityid;
      }
      else if($scope.selectedEntity==='REQUEST'){
        url = '/createrequest/'+e.entityid;
      }
      else if($scope.selectedEntity==='POLICY'){
        url = '/goipolicy/'+e.entityid;
      }else{
        //do nothing.
      }
      if($scope.selectedEntity!=='SELECT'){
        $location.url(url);  
      }
       
    }//end editUser
}]);
