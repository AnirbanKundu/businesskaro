angular
  .module('theme.demos.adminmanage', ['ui.bootstrap','theme.core.search_controller'])
  .controller('EntityManagementController', ['$rootScope','$scope', '$http', '$location','$modal','$log', 'LookUpService',function ($rootScope, $scope, $http, $location,$modal,$log, LookUpService) {

    $scope.waiting = false;
    $scope.SELECT = "";
    $scope.selectedEntity ="";
    $scope.selectedIndustry ="";
    $scope.selectedState = "";

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
      if($scope.selectedEntity==$scope.SELECT)
      {
         $scope.showServerMessage = 'Please select mandatory field';
      }
      if($scope.selectedIndustry==$scope.SELECT)
      {
        $scope.showServerMessage = 'Please select mandatory field';
      }
      if($scope.selectedState==$scope.SELECT)
      {
        $scope.showServerMessage = 'Please select mandatory field';
      }
      if($scope.selectedEntity!=$scope.SELECT && $scope.selectedIndustry!=$scope.SELECT && $scope.selectedState!=$scope.SELECT){

        //$scope.selectedState =  $scope.selectedState ==='ALL' ? -1 : $scope.selectedState; 
        //$scope.selectedIndustry = $scope.selectedIndustry === 'ALL' ? -1 : $scope.selectedIndustry;

        $scope.waiting = true;
        $scope.showServerMessage = "";
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
            });
      }     
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